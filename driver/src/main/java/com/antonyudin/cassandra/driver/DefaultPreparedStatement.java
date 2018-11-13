
/*
 * Copyright Anton Yudin, https://antonyudin.com/software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * vim: set nowrap:
 *
 */
 
package com.antonyudin.cassandra.driver;


import java.nio.ByteBuffer;

import java.math.BigDecimal;

import java.util.logging.Logger;

import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

import java.sql.SQLException;
import java.sql.ResultSet;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.QueryTrace;
import com.datastax.driver.core.LocalDate;


public class DefaultPreparedStatement extends AbstractPreparedStatement {

	private final static Logger logger = Logger.getLogger(DefaultPreparedStatement.class.getName());


	private final DefaultConnection connection;
	private final Session session;
	private final PreparedStatementsCache preparedStatementsCache;
	private final int resultSetType;
	private final int resultSetConcurrency;
	private final String originalSQL;
	private final String sqlWithoutBeginningComments;
	private final String beginningComments;
//	private final String effectiveSQL;

	private final PreparedStatement preparedStatement;
	private BoundStatement boundStatement = null;
	private BatchStatement batchStatement = null;
	private final boolean insert;
	private final boolean select;

	private final Warnings warnings = new Warnings();

	private final Driver.Context driverContext;
	private final DefaultConnection.Context connectionContext;

	private boolean enableTracingRequested = false;
	private boolean allowFilteringRequested = false;

	private final CommentsProcessor commentsProcessor;



	DefaultPreparedStatement(
		final Driver.Context driverContext,
		final DefaultConnection.Context connectionContext,
		final DefaultConnection connection,
		final Session session,
		final PreparedStatementsCache preparedStatementsCache,
		final String sql,
		final int resultSetType,
		final int resultSetConcurrency
	) throws SQLException {
		this.driverContext = driverContext;
		this.connectionContext = connectionContext;
		this.connection = connection;
		this.session = session;
		this.preparedStatementsCache = preparedStatementsCache;
		this.originalSQL = sql;
		this.resultSetType = resultSetType;
		this.resultSetConcurrency = resultSetConcurrency;

		this.beginningComments = parseBeginningComments(this.originalSQL);

//		logger.info("beginningComments: [" + beginningComments + "]");

		this.commentsProcessor = new CommentsProcessor(
			beginningComments,
			new CommentsProcessor.Options() {
				@Override
				public void enableTracing() {
					enableTracingRequested = true;
				}
				@Override
				public void allowFiltering() {
					allowFilteringRequested = true;
				}
			}
		);

		this.sqlWithoutBeginningComments = stripBeginningComments(
			this.originalSQL, this.beginningComments
		);

//		logger.info("sqlWithoutBeginningComments: [" + sqlWithoutBeginningComments + "]");

		this.insert = sqlWithoutBeginningComments.startsWith("insert");
		this.select = sqlWithoutBeginningComments.startsWith("select");

	//	this.effectiveSQL = transformSQL(this.originalSQL);

	//	logger.info("effectiveSQL: [" + effectiveSQL + "]");

		try {

			this.preparedStatement = this.preparedStatementsCache.get(
				this.sqlWithoutBeginningComments +
				(
					(this.select && allowFilteringRequested)?
					" ALLOW FILTERING":
					""
				)
			);

		} catch (java.lang.Exception exception) {
			throw new SQLException(exception);
		}
	}

	protected String stripBeginningComments(final String query, final String comment) {

		if (comment == null)
			return query;

		return query.substring(comment.length(), query.length()).trim();
	}

	protected String parseBeginningComments(final String query) {

		if (query.startsWith("/*")) {

			final int index = query.indexOf("*/");

			if (index > 0)
				return query.substring(0, index + 2);
		}

		return null;
	}

	protected void applyTracing(final Statement statement) {
		if (connectionContext.isTracingEnabled() || enableTracingRequested) {
			logger.info("enabling tracing ...");
			statement.enableTracing();
		}
	}

	protected void processTracing(final com.datastax.driver.core.ResultSet resultSet) {

		if (connectionContext.isTracingEnabled() || enableTracingRequested) {

			final QueryTrace queryTrace = resultSet.getExecutionInfo().getQueryTrace();

			logger.info(
				"TRACE: id [" + queryTrace.getTraceId() + "], coordinator [" +
				queryTrace.getCoordinator() + "]"
			);

			for (QueryTrace.Event event: queryTrace.getEvents()) {
				logger.info(
					"TRACE: [" + event.getTimestamp() + "] [" +
					event.getSource() + "] [" +
					event.getDescription() + "]"
				);
			}

			logger.info("TRACE: duration [" + queryTrace.getDurationMicros() + "]");
		}
	}


	@Override
	public int[] executeBatch() throws SQLException {

		final int numberOfStatements = getBatchStatement().getStatements().size();

//		logger.info("BATCH [" + numberOfStatements + "]");

		applyTracing(getBatchStatement());

		final com.datastax.driver.core.ResultSet resultSet = session.execute(getBatchStatement());

		processTracing(resultSet);

//		logger.info("resultSet: [" + resultSet + "]");

		warnings.add(resultSet);

		if (insert) {

			for (int i = 0;; i++) {

				final Row row = resultSet.one();

			//	logger.info("got row: [" + row + "]");

				if (row == null)
					break;

				for (int j = 0; j < row.getColumnDefinitions().size(); j++) {
					logger.info("batch[" + i + "][" + j + "]: " + row.getObject(j));
				}
			}

//			return numberOfStatements;
/*
			final List<ExecutionInfo> allExecutionInfo = resultSet.getAllExecutionInfo();

			final int[] result = new int[allExecutionInfo.size()];
*/
			final int[] result = new int[numberOfStatements];

			for (int i = 0; i < result.length; i++) {
				result[i] = 1;
			}

			return result;

		} else {

			final List<Integer> result = new ArrayList<>();

			final Iterator<com.datastax.driver.core.Row> iterator = resultSet.iterator();

			while (iterator.hasNext()) {

				final com.datastax.driver.core.Row row = iterator.next();

				if (row == null)
					break;
			}

			return null;
		}
	}


	@Override
	public void clearBatch() throws SQLException {
		getBatchStatement().clear();
	}


	@Override
	public int executeUpdate() throws SQLException {

		final ResultSet resultSet = executeQuery();

		// XXX

		return 1;
	}


	protected BoundStatement getBoundStatement() {

		if (boundStatement == null) {

			boundStatement = preparedStatement.bind();

			if (fetchSize > 0)
				boundStatement.setFetchSize(fetchSize);
		}

		return boundStatement;
	}


	protected BatchStatement getBatchStatement() {

		if (batchStatement == null) {

			batchStatement = new BatchStatement();

			if (fetchSize > 0)
				batchStatement.setFetchSize(fetchSize);
		}

		return batchStatement;
	}


	private final boolean logContent = false;

	protected void log(final String message) {
		if (logContent)
			logger.finest(message);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		log("setNull(" + parameterIndex + ")");
		getBoundStatement().setToNull(parameterIndex - 1);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		log("setBoolean(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setBool(parameterIndex - 1, x);
	}


	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		log("setByte(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setByte(parameterIndex - 1, x);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		log("setShort(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setShort(parameterIndex - 1, x);
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		log("setInt(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setInt(parameterIndex - 1, x);
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		log("setLong(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setLong(parameterIndex - 1, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		log("setFloat(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setFloat(parameterIndex - 1, x);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		log("setDouble(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setDouble(parameterIndex - 1, x);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		log("setBigDecimal(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setDecimal(parameterIndex - 1, x);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		log("setString(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setString(parameterIndex - 1, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte x[]) throws SQLException {
		log("setBytes(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setBytes(parameterIndex - 1, ByteBuffer.wrap(x));
	}

	@Override
	public void setTimestamp(int parameterIndex, java.sql.Timestamp x) throws SQLException {
		log("setTimestamp(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setTimestamp(parameterIndex - 1, x);
	}

	@Override
	public void setDate(final int parameterIndex, final java.sql.Date x) throws SQLException {
		log("setDate(" + parameterIndex + ", " + x + ")");
		getBoundStatement().setDate(
			parameterIndex - 1,
			(x != null? LocalDate.fromMillisSinceEpoch(x.getTime()): null)
		);
	}


	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		log("setObject(" + parameterIndex + ", " + x + ", " + targetSqlType + ")");

		if (x == null) {
			getBoundStatement().setToNull(parameterIndex - 1);
			return;
		}

		if (x instanceof UUID) {
			getBoundStatement().setUUID(parameterIndex - 1, (UUID) x);
		} else if (x instanceof List) {

			log("types (" +
				x.getClass().getTypeParameters()[0].getName() + ", " +
				x.getClass().getTypeParameters()[0].getTypeName() +
				") {"
			);

			for (java.lang.reflect.Type type: x.getClass().getTypeParameters()[0].getBounds()) {
				log("\ttype: [" + type + "]");
			}

			log("}");

			getBoundStatement().setList(parameterIndex - 1, (List) x);
		} else if (x instanceof Map) {

			log("types (" +
				x.getClass().getTypeParameters()[0].getName() + ", " +
				x.getClass().getTypeParameters()[0].getTypeName() +
				") {"
			);

			for (java.lang.reflect.Type type: x.getClass().getTypeParameters()[0].getBounds()) {
				log("\ttype: [" + type + "]");
			}

			log("}");

			getBoundStatement().setMap(parameterIndex - 1, (Map) x);
		} else
			throw new IllegalArgumentException("class [" + x.getClass() + "] is not supported");
	}

	@Override
	public void addBatch() throws SQLException {
		log("addBatch()");
		getBatchStatement().add(getBoundStatement());
		boundStatement = null;
	}

	@Override
	public int getMaxRows() throws SQLException {
		return getBoundStatement().getFetchSize();
	//	return 0; // unlimited
	}

	@Override
	public void setMaxRows(final int value) throws SQLException {
		logger.info("setMaxRows(" + value + ")");
		getBoundStatement().setFetchSize(value);
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return 0; // no timeout
	}

	private int fetchSize = -1;

	@Override
	public void setFetchSize(final int rows) throws SQLException {

		log("setFetchSize(" + rows + ")");

		fetchSize = rows;

		if (batchStatement != null) {
			batchStatement.setFetchSize(rows);
			for (Statement statement: batchStatement.getStatements())
				statement.setFetchSize(rows);
		}

		if (boundStatement != null)
			boundStatement.setFetchSize(rows);
	}


	@Override
	public ResultSet executeQuery() throws SQLException {

		final Statement statement = (
			batchStatement != null?
			batchStatement:
			getBoundStatement()
		);

		if (batchStatement != null) {
			logger.info("BATCH [" + batchStatement.getStatements().size() + "]");
		}

		applyTracing(statement);

		final com.datastax.driver.core.ResultSet resultSet = session.execute(statement);

		processTracing(resultSet);

		warnings.add(resultSet);

		return new CassandraResultSet(driverContext, resultSet);
	}


	@Override
	public void close() throws SQLException {
		return;	// XXX looks like there is nothing to close in datastax driver
	}


}

