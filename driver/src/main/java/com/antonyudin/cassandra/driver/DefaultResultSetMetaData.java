
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


public class DefaultResultSetMetaData extends AbstractResultSetMetaData {

	private final java.util.logging.Logger logger =java.util.logging.Logger.getLogger(
		DefaultResultSetMetaData.class.getName()
	);


	public DefaultResultSetMetaData(final String[] columnNames) {
		this.columnNames = columnNames;
	}


	private final String[] columnNames;

	// XXX not used by hibernate
	@Override
	public int getColumnCount() throws SQLException {
		return columnNames.length;
	}

	// XXX not used by hibernate
	@Override
	public String getColumnName(final int column) throws SQLException {
		return columnNames[column];
	}

	// XXX not used by hibernate
	@Override
	public String getColumnLabel(final int column) throws SQLException {
		return columnNames[column];
	}

	// XXX not used by hibernate
	@Override
	public String getTableName(final int column) throws SQLException {
		return null;
	}

	// XXX not used by hibernate
	@Override
	public String getColumnTypeName(final int column) throws SQLException {
		return "TEXT";
	}

	// XXX not used by hibernate
	@Override
	public int getColumnType(final int column) throws SQLException {
		return java.sql.Types.VARCHAR;
	}

	// XXX not used by hibernate
	@Override
	public int getColumnDisplaySize(final int column) throws SQLException {
		return 64;
	}

	// XXX not used by hibernate
	@Override
	public int isNullable(final int column) throws SQLException {
		return columnNullableUnknown;
	}

	// XXX not used by hibernate
	@Override
	public boolean isAutoIncrement(final int column) throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public int getPrecision(final int column) throws SQLException {
		return 0;
	}

	// XXX not used by hibernate
	@Override
	public int getScale(final int column) throws SQLException {
		return 0;
	}

}

