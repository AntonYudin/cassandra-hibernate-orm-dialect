
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


import java.io.Reader;
import java.io.InputStream;

import java.math.BigDecimal;

import java.util.Calendar;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.Array;
import java.sql.SQLXML;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.ParameterMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;



public abstract class AbstractPreparedStatement extends AbstractStatement implements PreparedStatement {



	@Override
	public ResultSet executeQuery() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int executeUpdate() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setByte(final int parameterIndex, final byte x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setShort(final int parameterIndex, final short x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setInt(final int parameterIndex, final int x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setLong(final int parameterIndex, final long x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setFloat(final int parameterIndex, final float x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setDouble(final int parameterIndex, final double x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setString(final int parameterIndex, final String x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBytes(final int parameterIndex, final byte x[]) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setDate(final int parameterIndex, final java.sql.Date x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setTime(final int parameterIndex, final java.sql.Time x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setTimestamp(final int parameterIndex, final java.sql.Timestamp x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setAsciiStream(
		final int parameterIndex,
		final java.io.InputStream x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setUnicodeStream(
		final int parameterIndex,
		final java.io.InputStream x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBinaryStream(
		final int parameterIndex,
		final java.io.InputStream x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void clearParameters() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setObject(
		final int parameterIndex,
		final Object x,
		final int targetSqlType
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setObject(final int parameterIndex, final Object x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean execute() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void addBatch() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setCharacterStream(
		final int parameterIndex,
		final java.io.Reader reader,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setRef(final int parameterIndex, final Ref x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setClob(final int parameterIndex, final Clob x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setArray(final int parameterIndex, final Array x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setDate(
		final int parameterIndex,
		final java.sql.Date x,
		final Calendar cal
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setTime(
		final int parameterIndex,
		final java.sql.Time x,
		final Calendar cal) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setTimestamp(
		final int parameterIndex,
		final java.sql.Timestamp x,
		final Calendar cal
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNull(
		final int parameterIndex,
		final int sqlType,
		final String typeName
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setURL(
		final int parameterIndex,
		final java.net.URL x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNString(final int parameterIndex, final String value) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNCharacterStream(
		final int parameterIndex,
		final Reader value,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setClob(
		final int parameterIndex,
		final Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBlob(
		final int parameterIndex,
		final InputStream inputStream,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNClob(
		final int parameterIndex,
		final Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setSQLXML(
		final int parameterIndex,
		final SQLXML xmlObject
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setObject(
		final int parameterIndex,
		final Object x,
		final int targetSqlType,
		final int scaleOrLength
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setAsciiStream(
		final int parameterIndex,
		final java.io.InputStream x,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBinaryStream(
		final int parameterIndex,
		final java.io.InputStream x,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setCharacterStream(
		final int parameterIndex,
		final java.io.Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setAsciiStream(
		final int parameterIndex,
		final java.io.InputStream x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBinaryStream(
		final int parameterIndex,
		final java.io.InputStream x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setCharacterStream(
		final int parameterIndex,
		final java.io.Reader reader
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNCharacterStream(
		final int parameterIndex,
		final Reader value
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setClob(
		final int parameterIndex,
		final Reader reader
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setBlob(
		final int parameterIndex,
		final InputStream inputStream
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
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

