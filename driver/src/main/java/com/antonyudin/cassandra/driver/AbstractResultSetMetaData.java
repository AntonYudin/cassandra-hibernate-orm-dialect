
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


import java.sql.SQLException;


public abstract class AbstractResultSetMetaData implements java.sql.ResultSetMetaData {

	@Override
	public int getColumnCount() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isAutoIncrement(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isCaseSensitive(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isSearchable(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isCurrency(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int isNullable(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isSigned(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getColumnDisplaySize(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getColumnLabel(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getColumnName(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getSchemaName(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getPrecision(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getScale(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getTableName(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getCatalogName(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getColumnType(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getColumnTypeName(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isReadOnly(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isWritable(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isDefinitelyWritable(final int column) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getColumnClassName(final int column) throws SQLException {
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
