
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


import java.util.logging.Logger;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.ResultSet;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.SimpleStatement;


public class DefaultStatement extends AbstractStatement {

	private final static Logger logger = Logger.getLogger(DefaultStatement.class.getName());


	private final DefaultConnection connection;
	private final Session session;
	private final Warnings warnings = new Warnings();
	private final Driver.Context driverContext;


	DefaultStatement(
		final Driver.Context driverContext,
		final DefaultConnection connection,
		final Session session
	) {
		this.driverContext = driverContext;
		this.connection = connection;
		this.session = session;
	}


	private boolean closed = false;

	@Override
	public void close() throws SQLException {
		closed = true;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return warnings.getSQLWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		warnings.clear();
	}


	private	com.datastax.driver.core.ResultSet resultSet = null;


	@Override
	public boolean execute(final String sql) throws SQLException {

		final String transformedSQL = transformSQL(sql);

		if (transformedSQL.length() > 0) {

			final Statement statement = new SimpleStatement(transformedSQL);

			if (getMaxRows() > 0)
				statement.setFetchSize(getMaxRows());

			resultSet = session.execute(statement);

			warnings.add(resultSet);
		}

		return true;
	}

	// XXX not used by hibernate
	@Override
	public ResultSet executeQuery(final String sql) throws SQLException {

		final String transformedSQL = transformSQL(sql);

		if (transformedSQL.length() > 0) {

			final Statement statement = new SimpleStatement(transformedSQL);

			if (getMaxRows() > 0)
				statement.setFetchSize(getMaxRows());

			resultSet = session.execute(statement);

			warnings.add(resultSet);

			return new CassandraResultSet(driverContext, resultSet);
		}

		return null;
	}



	protected String transformSQL(final String originalSQL) {
		return connection.getTransformer().transform(originalSQL);
	}

	@Override
	public boolean isClosed() throws SQLException {
		return closed;
	}


	private int maxRows = 0;

	// XXX not used by hibernate
	@Override
	public void setMaxRows(final int max) throws SQLException {
		maxRows = max;
	}

	@Override
	public int getMaxRows() throws SQLException {
		return maxRows;
	}



	// XXX not used by hibernate
	@Override
	public int getUpdateCount() throws SQLException {
		return -1;
	}

	// XXX not used by hibernate
	@Override
	public ResultSet getResultSet() throws SQLException {
		return new CassandraResultSet(driverContext, resultSet);
	}

}

