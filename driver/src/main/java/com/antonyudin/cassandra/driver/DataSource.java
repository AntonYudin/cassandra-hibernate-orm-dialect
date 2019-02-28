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
import java.sql.DriverManager;
import java.sql.Connection;


public class DataSource implements javax.sql.DataSource {

	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		DataSource.class.getName()
	);

	private String serverName = "localhost";
	private String databaseName = "";
	private String user = null;
	private String password = null;
	private int portNumber = 0;


	static {
		try {
			Class.forName("com.antonyudin.cassandra.driver.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnection(user, password);
	}


	@Override
	public Connection getConnection(final String user, final String password) throws SQLException {
		try {
			logger.fine(()-> "getConnection(" + user + ",)");

			final Connection connection = DriverManager.getConnection(getUrl(), user, password);

			return connection;
		} catch (SQLException exception) {
			logger.warning("exception: " + exception);
			throw exception;
		}
	}


	@Override
	public java.io.PrintWriter getLogWriter() {
		return null;
	}


	@Override
	public void setLogWriter(final java.io.PrintWriter printWriter) {
	}


	public String getServerName() {
		return serverName;
	}

	public void setServerName(final String value) {
		if ((value == null) || (value.length() <= 0)) {
			serverName = "localhost";
		} else {
			serverName = value;
		}
	}


	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(final String value) {
		databaseName = value;
	}


	public String getUser() {
		return user;
	}

	public void setUser(final String value) {
		user = value;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(final String value) {
		password = value;
	}


	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(final int value) {
    		portNumber = value;
	}

	public String getUrl() {

		logger.fine(()-> "getUrl()");

		final StringBuilder result = new StringBuilder();

		result.append("jdbc:cassandra://");
		result.append(getServerName());

		if (getPortNumber() != 0)
			result.append(":").append(getPortNumber());

		result.append("/");
		result.append(getDatabaseName());

		result.append("?a=b");

		if (getTransformer() != null) {
			result.append("&transformer=");
			result.append(getTransformer());
		}

		if ((getAllowFiltering() != null) && getAllowFiltering().equalsIgnoreCase("true")) {
			result.append("&allowFiltering=true");
		}

		return result.toString();
	}


	private int loginTimeout = 1 * 60;

	@Override
	public int getLoginTimeout() {
		return loginTimeout;
	}

	@Override
	public void setLoginTimeout(final int value) {
		loginTimeout = value;
	}



	private String transformer;

	public String getTransformer() {
		return transformer;
	}

	public void setTransformer(final String value) {
		logger.fine(()-> "setTransformer(" + value + ")");
		transformer = value;
	}


	private String allowFiltering;

	public String getAllowFiltering() {
		return allowFiltering;
	}

	public void setAllowFiltering(final String value) {
		logger.fine(()-> "setAllowFiltering(" + value + ")");
		allowFiltering = value;
	}


	@Override
	public java.util.logging.Logger getParentLogger() {
		return java.util.logging.Logger.getLogger("com.antonyudin.cassandra");
	}


	@Override
	public boolean isWrapperFor(final Class<?> interfaceInstance) throws SQLException {
	    return interfaceInstance.isAssignableFrom(getClass());
	}

	@Override
	public <T> T unwrap(final Class<T> interfaceInstance) throws SQLException {

		if (interfaceInstance.isAssignableFrom(getClass()))
			return interfaceInstance.cast(this);

		throw new SQLException("cannot unwrap[" + interfaceInstance.getName() + "]");
	}

}

