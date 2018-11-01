
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


import java.util.Properties;

import java.util.logging.Logger;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.Connection;
import java.sql.Types;

import com.datastax.driver.core.DataType;


public class Driver implements java.sql.Driver {

	private static final Logger logger;

	static {
		try {
			logger = Logger.getLogger(Driver.class.getName());
			register();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}


	private final Context context = new Context();


	protected static void deregister() {
		logger.info("deregister()");
	}

	protected static void register() throws SQLException {

		logger.info("register()");

		final Driver driverInstance = new Driver();

		DriverManager.registerDriver(driverInstance, ()->driverInstance.deregister());
	}


	private static final Logger parentLogger = Logger.getLogger(Driver.class.getPackage().getName());

	@Override
	public Logger getParentLogger() {
		return parentLogger;
	}

	@Override
	public boolean jdbcCompliant() {
		return false;
	}

	@Override
	public int getMinorVersion() {
		return 1;
	}

	@Override
	public int getMajorVersion() {
		return 0;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(final String url, final Properties info) {
		logger.info("getPropertyInfo(" + url + ", " + info + ")");
		throw new UnsupportedOperationException("not implemented yet");
	}


	@Override
	public boolean acceptsURL(final String url) throws SQLException {
		logger.info("acceptURL(" + url + ")");
		try {
			final ConnectionString connectionString = getConnectionString(url);
			return true;
		} catch (java.lang.Exception exception) {
			logger.warning("exception: " + exception);
			return false;
		}
	}

	protected ConnectionString getConnectionString(final String url) throws java.lang.Exception {
		return new ConnectionString(url);
	}


	@Override
	public Connection connect(final String url, final Properties info) throws SQLException {
		logger.info("connect(" + url + ", " + info + ")");

		try {
			final ConnectionString connectionString = getConnectionString(url);

			return new DefaultConnection(context, connectionString);

		} catch (java.lang.Exception exception) {
			logger.warning("exception: " + exception);
			throw new SQLException(exception);
		}
	}


	public static class Context extends TypeConverter {
	}

	public static class TypeConverter implements java.io.Serializable {


		public int getPrecisionForType(final DataType type) {

			if (type == DataType.cfloat()) {
				return 1;
			} else if (type == DataType.cdouble()) {
				return 2;
			} else if (type == DataType.decimal()) {
				return 10;
			} else if (type == DataType.bigint()) {
				return 0;
			}
			return 0;
		}


		public int getScaleForType(final DataType type) {

			if (type == DataType.cfloat()) {
				return 7;
			} else if (type == DataType.cdouble()) {
				return 16;
			} else if (type == DataType.decimal()) {
				return 10;
			}

			return 0;
		}

		public int typeFromCassandraToSQL(final DataType type) {
			if (type == DataType.ascii())
				return Types.VARCHAR;
			else if (type == DataType.varchar())
				return Types.VARCHAR;
			else if (type == DataType.text())
				return Types.VARCHAR;
			else if (type == DataType.bigint())
				return Types.BIGINT;
			else if (type == DataType.uuid())
				return Types.OTHER;
			else if (type == DataType.timestamp())
				return Types.TIMESTAMP;
			else if (type == DataType.date())
				return Types.TIMESTAMP;
			else if (type == DataType.cint())
				return Types.INTEGER;
			else if (type == DataType.cboolean())
				return Types.BOOLEAN;
			else if (type == DataType.blob())
				return Types.BLOB;
			else if (type == DataType.cdouble())
				return Types.DOUBLE;
			else if (type == DataType.cfloat())
				return Types.FLOAT;
			else if (type == DataType.smallint())
				return Types.SMALLINT;
			else if (type == DataType.smallint())
				return Types.SMALLINT;

			if (type instanceof DataType.CollectionType) {
				logger.finest(
					"collection type: [" +
					((DataType.CollectionType) type).getTypeArguments() + "]"
				);
				logger.finest("\treturning Types.JAVA_OBJECT");
				return Types.JAVA_OBJECT;
			}

			throw new IllegalArgumentException("type [" + type.getName() + "] is not supported");
		}
	}


}

