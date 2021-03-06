
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


import java.util.List;
import java.util.ArrayList;

import java.sql.SQLException;
import java.sql.ResultSetMetaData;


public class DefaultResultSet extends AbstractResultSet {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		DefaultResultSet.class.getName()
	);

	private boolean logContent = true;


	public DefaultResultSet(final String[] columnNames, final List<Object[]> rows) {

		logger.info(()->
			"DefaultResultSet(), rows.size: [" + (rows != null? rows.size(): null) +
			"], logContent: [" + logContent + "]"
		);

		this.columnNames = columnNames;

		if (logContent) {
			for (String columnName: columnNames) {
				logger.info(()-> "\tcolumnName: [" + columnName + "]");
			}
		}

		this.rows.addAll(rows);

		for (int i = 0; i < rows.size(); i++) {

			final Object[] row = rows.get(i);

			final StringBuilder line = new StringBuilder();

			for (Object item: row) {
				if (line.length() > 0)
					line.append(", ");
				line.append(item);
			}

			if (logContent)
				logger.info("\trow[" + i + "]: " + line);
		}
	}


	private int position = -1;

	private final String[] columnNames;
	private final List<Object[]> rows = new ArrayList<>();


	@Override
	public boolean next() throws SQLException {

		position++;

		if (position >= rows.size())
			return false;

		return true;
	}


	private boolean closed = false;

	@Override
	public void close() throws SQLException {
		closed = true;
	}


	@Override
	public String getString(final String columnLabel) throws SQLException {

		final Object value = getCurrentRow()[getColumnIndexByColumnName(columnLabel)];

		wasNullFlag = (value == null);

		return (value != null? value.toString(): null);
	}

	// XXX not used by hibernate
	@Override
	public String getString(final int columnIndex) throws SQLException {

		final Object value = getCurrentRow()[columnIndex - 1];

		wasNullFlag = (value == null);

		return (value != null? value.toString(): null);
	}

	// XXX not used by hibernate
	@Override
	public Object getObject(final int columnIndex) throws SQLException {
		return getCurrentRow()[columnIndex - 1];
	}



	protected int getColumnIndexByColumnName(final String columnName) {

		for (int i = 0; i < columnNames.length; i++) {
			if (isEqual(columnNames[i], columnName))
				return i;
		}

		throw new IllegalArgumentException("cannot find column [" + columnName + "]");
	}


	protected boolean isEqual(final Object item0, final Object item1) {
		if ((item0 == null) && (item1 == null))
			return true;

		if ((item0 == null) || (item1 == null))
			return false;

		return item0.equals(item1);
	}


	protected Object[] getCurrentRow() {

		if (position < 0)
			throw new IllegalArgumentException("position is < 0: [" + position + "]");

		if (position >= rows.size())
			throw new IllegalArgumentException("position > rows.size: [" + position + ", " + rows.size() + "]");

		return rows.get(position);
	}


	@Override
	public short getShort(final String columnLabel) throws SQLException {

		final Short result = (Short) getCurrentRow()[getColumnIndexByColumnName(columnLabel)];

		wasNullFlag = (result == null);

		if (result == null)
			return -1;

		return result.shortValue();
	}

	@Override
	public int getInt(final String columnLabel) throws SQLException {

		final Integer result = (Integer) getCurrentRow()[getColumnIndexByColumnName(columnLabel)];

		wasNullFlag = (result == null);

		if (result == null)
			return -1;

		return result.intValue();
	}

	// XXX not used by hibernate
	@Override
	public int getInt(final int columnIndex) throws SQLException {

		final Integer result = (Integer) getCurrentRow()[columnIndex - 1];

		wasNullFlag = (result == null);

		if (result == null)
			return -1;

		return result.intValue();
	}


	@Override
	public long getLong(final String columnLabel) throws SQLException {

		final Number result = (Number) getCurrentRow()[getColumnIndexByColumnName(columnLabel)];

		wasNullFlag = (result == null);

		if (result == null)
			return -1;

		return result.longValue();
	}

	// XXX not used by hibernate
	@Override
	public long getLong(final int columnIndex) throws SQLException {

		final Number result = (Number) getCurrentRow()[columnIndex - 1];

		wasNullFlag = (result == null);

		if (result == null)
			return -1;

		return result.longValue();
	}


	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return new DefaultResultSetMetaData(
			columnNames
		);
	}

	
	private boolean wasNullFlag = false;

	// XXX not used by hibernate
	@Override
	public boolean wasNull() throws SQLException {
		return wasNullFlag;
	}

}

