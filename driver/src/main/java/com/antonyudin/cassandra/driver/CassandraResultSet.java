
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


import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

import javax.sql.rowset.serial.SerialBlob;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.DataType;


public class CassandraResultSet extends AbstractResultSet {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		CassandraResultSet.class.getName()
	);

	
	private final Driver.Context context;
	private final ResultSet resultSet;


	public CassandraResultSet(final Driver.Context context, final ResultSet resultSet) {
		this.context = context;
		this.resultSet = resultSet;
	}

	private final boolean logContent = false;

	protected void log(final String message) {
		if (logContent)
			logger.finest(message);
	}


	private Row currentRow;

	@Override
	public boolean next() throws SQLException {
		currentRow = resultSet.one();
		return (currentRow != null);
	}


	private boolean closed = false;

	@Override
	public void close() throws SQLException {
		closed = true;
	}


	private boolean lastWasNull = false;

	@Override
	public boolean wasNull() throws SQLException {
		return lastWasNull;
	}

	@Override
	public String getString(final String columnLabel) throws SQLException {
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getString(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public String getString(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getString(columnIndex - 1);
	}

	@Override
	public boolean getBoolean(final String columnLabel) throws SQLException {
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getBool(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public boolean getBoolean(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getBool(columnIndex - 1);
	}

	@Override
	public byte getByte(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public short getShort(final String columnLabel) throws SQLException {
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getShort(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public short getShort(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getShort(columnIndex - 1);
	}

	@Override
	public int getInt(final String columnLabel) throws SQLException {
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getInt(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public int getInt(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getInt(columnIndex - 1);
	}

	@Override
	public long getLong(final String columnLabel) throws SQLException {
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getLong(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public long getLong(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getLong(columnIndex - 1);
	}

	@Override
	public float getFloat(final String columnLabel) throws SQLException {
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getFloat(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public float getFloat(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getFloat(columnIndex - 1);
	}

	@Override
	public double getDouble(final String columnLabel) throws SQLException {
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getDouble(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public double getDouble(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getDouble(columnIndex - 1);
	}


	@Override
	public java.sql.Timestamp getTimestamp(final String columnLabel) throws SQLException {

		lastWasNull = currentRow.isNull(columnLabel);

		return (
			lastWasNull?
			null:
			new java.sql.Timestamp(currentRow.getTimestamp(columnLabel).getTime())
		);
	}

	// XXX not used by hibernate
	@Override
	public java.sql.Timestamp getTimestamp(final int columnIndex) throws SQLException {

		lastWasNull = currentRow.isNull(columnIndex - 1);

		return (
			lastWasNull?
			null:
			new java.sql.Timestamp(currentRow.getTimestamp(columnIndex - 1).getTime())
		);
	}



	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return new CassandraResultSetMetaData(context, resultSet);
	}

	@Override
	public Object getObject(final String columnLabel) throws SQLException {
		log("getObject(" + columnLabel + ")");
		lastWasNull = currentRow.isNull(columnLabel);
		return currentRow.getObject(columnLabel);
	}

	// XXX not used by hibernate
	@Override
	public Object getObject(final int columnIndex) throws SQLException {
		lastWasNull = currentRow.isNull(columnIndex - 1);
		return currentRow.getObject(columnIndex - 1);
	}

	@Override
	public BigDecimal getBigDecimal(final String columnLabel) throws SQLException {
		log("getBigDecimal(" + columnLabel + ")");

		lastWasNull = currentRow.isNull(columnLabel);

		if (lastWasNull)
			return null;

		final DataType type = resultSet.getColumnDefinitions().getType(columnLabel);

		if (type == DataType.bigint())
			return new BigDecimal(currentRow.getLong(columnLabel));

		return currentRow.getDecimal(columnLabel);
	}

	@Override
	public Blob getBlob(final String columnLabel) throws SQLException {

		lastWasNull = currentRow.isNull(columnLabel);

		if (lastWasNull)
			return null;

		final java.nio.ByteBuffer buffer = currentRow.getBytes(columnLabel);

		if (buffer == null) {
			lastWasNull = true;
			return null;
		}

		final byte[] result = new byte[buffer.remaining()];

		buffer.get(result);

		return new SerialBlob(result);
	}

	// XXX not used by hibernate
	@Override
	public Blob getBlob(final int columnIndex) throws SQLException {

		lastWasNull = currentRow.isNull(columnIndex - 1);

		if (lastWasNull)
			return null;

		final java.nio.ByteBuffer buffer = currentRow.getBytes(columnIndex - 1);

		if (buffer == null) {
			lastWasNull = true;
			return null;
		}

		final byte[] result = new byte[buffer.remaining()];

		buffer.get(result);

		return new SerialBlob(result);
	}

	// XXX not used by hibernate
	@Override
	public byte[] getBytes(final int columnIndex) throws SQLException {

		lastWasNull = currentRow.isNull(columnIndex - 1);

		if (lastWasNull)
			return null;

		final java.nio.ByteBuffer buffer = currentRow.getBytes(columnIndex - 1);

		if (buffer == null) {
			lastWasNull = true;
			return null;
		}

		final byte[] result = new byte[buffer.remaining()];

		buffer.get(result);

		return result;
	}


	@Override
	public java.sql.Date getDate(final String columnLabel) throws SQLException {

		lastWasNull = currentRow.isNull(columnLabel);

		return (
			lastWasNull?
			null:
			new java.sql.Date(currentRow.getDate(columnLabel).getMillisSinceEpoch())
		);
	}

	// XXX not used by hibernate
	@Override
	public java.sql.Date getDate(final int columnIndex) throws SQLException {

		lastWasNull = currentRow.isNull(columnIndex - 1);

		return (
			lastWasNull?
			null:
			new java.sql.Date(currentRow.getDate(columnIndex - 1).getMillisSinceEpoch())
		);
	}



}

