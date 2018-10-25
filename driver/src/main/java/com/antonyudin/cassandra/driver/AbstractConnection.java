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


import java.util.Properties;
import java.util.Map;
import java.util.concurrent.Executor;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.NClob;
import java.sql.SQLXML;
import java.sql.Array;
import java.sql.Struct;
import java.sql.SQLClientInfoException;


public abstract class AbstractConnection implements java.sql.Connection {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		AbstractConnection.class.getName()
	);


	@Override
	public Statement createStatement() throws SQLException {
		throw notImplemented();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql) throws SQLException {
		throw notImplemented();
	}

	@Override
	public CallableStatement prepareCall(final String sql) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String nativeSQL(final String sql) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setAutoCommit(final boolean autoCommit) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void commit() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void rollback() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void close() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw notImplemented();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setReadOnly(final boolean readOnly) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setCatalog(final String catalog) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getCatalog() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setTransactionIsolation(final int level) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		throw notImplemented();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
		throw notImplemented();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
		throw notImplemented();
	}

	@Override
	public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Map<String,Class<?>> getTypeMap() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setTypeMap(final Map<String,Class<?>> map) throws SQLException {
		throw notImplemented();
	}


	@Override
	public void setHoldability(final int holdability) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Savepoint setSavepoint(final String name) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void rollback(final Savepoint savepoint) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Statement createStatement(
		final int resultSetType,
		final int resultSetConcurrency,
            	final int resultSetHoldability
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public PreparedStatement prepareStatement(
		final String sql,
		final int resultSetType,
		final int resultSetConcurrency,
		final int resultSetHoldability
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public CallableStatement prepareCall(
		final String sql,
		final int resultSetType,
		final int resultSetConcurrency,
		final int resultSetHoldability
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
		throw notImplemented();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final int columnIndexes[]) throws SQLException {
		throw notImplemented();
	}

	@Override
	public PreparedStatement prepareStatement(final String sql, final String columnNames[]) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Clob createClob() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Blob createBlob() throws SQLException {
		throw notImplemented();
	}

	@Override
	public NClob createNClob() throws SQLException {
		throw notImplemented();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isValid(final int timeout) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
		throw notImplemented();
	}

	@Override
	public void setClientInfo(final Properties properties) throws SQLClientInfoException {
		throw notImplemented();
	}

	@Override
	public String getClientInfo(final String name) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
		throw notImplemented();
	}


	@Override
	public void setSchema(final String schema) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getSchema() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void abort(final Executor executor) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isWrapperFor(final java.lang.Class<?> clazz) throws SQLException {
		throw notImplemented();
	}

	@Override
	public <T> T unwrap(final java.lang.Class<T> clazz) throws SQLException {
		throw notImplemented();
	}

	protected UnsupportedOperationException notImplemented() throws UnsupportedOperationException {
		try {
			throw new UnsupportedOperationException("not implemented yet");
		} catch (UnsupportedOperationException exception) {
			exception.printStackTrace(System.err);
			return exception;
		}
	}

}

