/*
 * vim: set nowrap:
 *
 */
 
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


package com.antonyudin.cassandra.driver;


import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.SQLWarning;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLFeatureNotSupportedException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


public class DefaultConnection extends AbstractConnection {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		DefaultConnection.class.getName()
	);


	private final Cluster cluster;
	private final Session session;
	private final String keyspaceName;
	private final Driver.Context driverContext;
	private final Context context;
	private final PreparedStatementsCache preparedStatementsCache;
	private final ConnectionString connectionString;


	DefaultConnection(final Driver.Context driverContext, final ConnectionString connectionString) {

		this.driverContext = driverContext;
		this.connectionString = connectionString;
		this.context = new Context(connectionString.isTracingEnabled());

		final Cluster.Builder builder = Cluster.builder();

		for (String contactPoint: connectionString.getContactPoints()) {
			logger.info("adding contact point [" + contactPoint + "]");
			builder.addContactPoint(contactPoint);
		}

		logger.info("contact points: [" + builder.getContactPoints() + "]");

		cluster = builder.build();

		final String keyspaceNameFromConnectionString = connectionString.getKeyspace();

		logger.info("keyspace name from connection string: [" + keyspaceNameFromConnectionString + "]");

		if ((keyspaceNameFromConnectionString != null) && (keyspaceNameFromConnectionString.length() > 0))
			keyspaceName = keyspaceNameFromConnectionString;
		else
			keyspaceName = null;

		if ((keyspaceName != null) && (keyspaceName.length() > 0)) {
			logger.info("connecting to keyspace: [" + keyspaceName + "]");
			session = cluster.connect(keyspaceName);
		} else {
			logger.info("connecting to cluster without a specific keyspace ...");
			session = cluster.connect();
		}

		preparedStatementsCache = new PreparedStatementsCache(driverContext, this, session);
	}


	@Override
	public Statement createStatement() throws SQLException {
		return new DefaultStatement(this, session);
	}


	@Override
	public PreparedStatement prepareStatement(final String sql) throws SQLException {
		return prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	}


	private boolean autoCommit = true;

	private boolean autoCommitWarningIssued = false;

	@Override
	public void setAutoCommit(final boolean value) throws SQLException {

		autoCommit = value;

		if (!autoCommitWarningIssued) {
			logger.warning("setAutoCommit() called, the driver does not support transactions");
			autoCommitWarningIssued = true;
		}
	}

	@Override
	public boolean getAutoCommit() throws SQLException {

		if (!autoCommitWarningIssued) {
			logger.warning("getAutoCommit() called, the driver does not support transactions");
			autoCommitWarningIssued = true;
		}

		return autoCommit;
	}

	@Override
	public void commit() throws SQLException {
		if (!autoCommitWarningIssued) {
			logger.warning("commit() called, the driver does not support transactions");
			autoCommitWarningIssued = true;
		}
	}

	@Override
	public void close() throws SQLException {
		logger.finest("close()");
		session.close();
		cluster.close();
		preparedStatementsCache.clear();
	}

	@Override
	public boolean isClosed() throws SQLException {
		logger.finest("isClosed()");
		return session.isClosed();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return new DefaultDatabaseMetaData(driverContext, cluster, session, keyspaceName, this);
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return false;
	}


	private Integer transactionIsolation = null;

	@Override
	public void setTransactionIsolation(final int level) throws SQLException {
		logger.warning("setTransactionIsolation(" + level + ") not supported");
		transactionIsolation = level;
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return TRANSACTION_READ_UNCOMMITTED;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null; // no warnings
	}

	@Override
	public void clearWarnings() throws SQLException {
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {

		logger.finest("prepareStatement()");
		logger.finest("\tsql: [" + sql + "]");
		logger.finest("\tresultSetType: [" + resultSetType + ", " + resultSetTypeToString(resultSetType) + "]");
		logger.finest("\tresultSetConcurrency: [" + resultSetConcurrency + ", " + resultSetConcurrencyToString(resultSetConcurrency) + "]");

		return new DefaultPreparedStatement(
			driverContext,
			context,
			this,
			session,
			preparedStatementsCache,
			sql,
			resultSetType,
			resultSetConcurrency
		);
	}


	protected String resultSetTypeToString(final int value) {
		switch (value) {
			case ResultSet.TYPE_FORWARD_ONLY: return "TYPE_FORWARD_ONLY";
			case ResultSet.TYPE_SCROLL_INSENSITIVE: return "TYPE_SCROLL_INSENSITIVE";
			case ResultSet.TYPE_SCROLL_SENSITIVE: return "TYPE_SCROLL_SENSITIVE";
			default:
			      return "unknown";
		}
	}

	protected String resultSetConcurrencyToString(final int value) {
		switch (value) {
			case ResultSet.CONCUR_READ_ONLY: return "CONCUR_READ_ONLY";
			case ResultSet.CONCUR_UPDATABLE: return "CONCUR_UPDATABLE";
			default:
			      return "unknown";
		}
	}


	@Override
	public String getCatalog() throws SQLException {
		return null;
	}

	@Override
	public String getSchema() throws SQLException {
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}


	public static class Context implements java.io.Serializable {

		private final boolean tracingEnabled;

		public Context(final boolean tracingEnabled) {
			this.tracingEnabled = tracingEnabled;
		}


		public boolean isTracingEnabled() {
			return tracingEnabled;
		}
	}

}

