
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

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ColumnDefinitions;


public class CassandraResultSetMetaData extends AbstractResultSetMetaData {

	private final java.util.logging.Logger logger =java.util.logging.Logger.getLogger(
		CassandraResultSetMetaData.class.getName()
	);


	private final Driver.Context context;
	private final ResultSet resultSet;


	public CassandraResultSetMetaData(final Driver.Context context, final ResultSet resultSet) {
		this.context = context;
		this.resultSet = resultSet;
	}


	@Override
	public int getColumnCount() throws SQLException {
		return resultSet.getColumnDefinitions().size();
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		return resultSet.getColumnDefinitions().getName(column - 1);
	}

	@Override
	public int getPrecision(int column) throws SQLException {

		final int result = context.getPrecisionForType(
			resultSet.getColumnDefinitions().getType(column - 1)
		);

		return result;
	}

	@Override
	public int getScale(int column) throws SQLException {

		final int result = context.getScaleForType(
			resultSet.getColumnDefinitions().getType(column - 1)
		);
		return result;

	}

	@Override
	public int getColumnType(int column) throws SQLException {
		return context.typeFromCassandraToSQL(
			resultSet.getColumnDefinitions().getType(column - 1)
		);
	}


	// XXX not used by hibernate
	@Override
	public String getColumnName(final int column) throws SQLException {
		return resultSet.getColumnDefinitions().getName(column - 1);
	}


	// XXX not used by hibernate
	@Override
	public String getTableName(final int column) throws SQLException {
		return resultSet.getColumnDefinitions().getTable(column - 1);
	//	return ""; // not applicable
	}

	// XXX not used by hibernate
	@Override
	public String getColumnTypeName(final int column) throws SQLException {
		return resultSet.getColumnDefinitions().getType(column - 1).getName().toString();
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

}
