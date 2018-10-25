
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


import java.math.BigDecimal;
import java.util.Calendar;
import java.io.Reader;
import java.io.InputStream;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.SQLWarning;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.Ref;
import java.sql.SQLXML;
import java.sql.Array;
import java.sql.NClob;
import java.sql.RowId;


public abstract class AbstractResultSet implements ResultSet {

	@Override
	public boolean next() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void close() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean wasNull() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getString(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean getBoolean(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public byte getByte(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public short getShort(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getInt(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public long getLong(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public float getFloat(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public double getDouble(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@SuppressWarnings("deprecation")
	@Override
	public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
		throw notImplemented();
	}

	@Override
	public byte[] getBytes(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Date getDate(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Time getTime(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Timestamp getTimestamp(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.InputStream getAsciiStream(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@SuppressWarnings("deprecation")
	@Override
	public java.io.InputStream getUnicodeStream(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.InputStream getBinaryStream(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getString(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean getBoolean(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public byte getByte(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public short getShort(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getInt(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public long getLong(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public float getFloat(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public double getDouble(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@SuppressWarnings("deprecation")
	@Override
	public BigDecimal getBigDecimal(final String columnLabel, final int scale) throws SQLException {
		throw notImplemented();
	}

	@Override
	public byte[] getBytes(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Date getDate(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Time getTime(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Timestamp getTimestamp(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.InputStream getAsciiStream(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@SuppressWarnings("deprecation")
	@Override
	public java.io.InputStream getUnicodeStream(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.InputStream getBinaryStream(final String columnLabel) throws SQLException {
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
	public String getCursorName() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Object getObject(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Object getObject(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int findColumn(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.Reader getCharacterStream(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.Reader getCharacterStream(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public BigDecimal getBigDecimal(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isFirst() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isLast() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void beforeFirst() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void afterLast() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean first() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean last() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getRow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean absolute(final int row) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean relative(final int rows) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean previous() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setFetchDirection(final int direction) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void setFetchSize(final int rows) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getType() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNull(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateByte(final int columnIndex, final byte x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateShort(final int columnIndex, final short x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateInt(final int columnIndex, final int x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateLong(final int columnIndex, final long x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateFloat(final int columnIndex, final float x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateDouble(final int columnIndex, final double x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateString(final int columnIndex, final String x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBytes(final int columnIndex, final byte x[]) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateDate(final int columnIndex, final java.sql.Date x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateTime(final int columnIndex, final java.sql.Time x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateTimestamp(final int columnIndex, final java.sql.Timestamp x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateAsciiStream(
		final int columnIndex,
		final java.io.InputStream x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBinaryStream(
		final int columnIndex,
		final java.io.InputStream x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateCharacterStream(
		final int columnIndex,
		final java.io.Reader x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateObject(
		final int columnIndex,
		final Object x,
		final int scaleOrLength
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateObject(final int columnIndex, final Object x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNull(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBoolean(final String columnLabel, final boolean x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateByte(final String columnLabel, final byte x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateShort(final String columnLabel, final short x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateInt(final String columnLabel, final int x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateLong(final String columnLabel, final long x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateFloat(final String columnLabel, final float x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateDouble(final String columnLabel, final double x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBigDecimal(final String columnLabel, final BigDecimal x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateString(final String columnLabel, final String x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBytes(final String columnLabel, final byte x[]) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateDate(final String columnLabel, final java.sql.Date x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateTime(final String columnLabel, final java.sql.Time x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateTimestamp(
		final String columnLabel,
		final java.sql.Timestamp x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateAsciiStream(
		final String columnLabel,
		final java.io.InputStream x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBinaryStream(
		final String columnLabel,
		final java.io.InputStream x,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateCharacterStream(
		final String columnLabel,
		final java.io.Reader reader,
		final int length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateObject(
		final String columnLabel,
		final Object x,
		final int scaleOrLength
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateObject(final String columnLabel, final Object x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void insertRow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateRow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void deleteRow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void refreshRow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Statement getStatement() throws SQLException {
		throw notImplemented();
	}

	@Override
	public Object getObject(
		final int columnIndex,
		final java.util.Map<String,Class<?>> map
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Ref getRef(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Blob getBlob(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Clob getClob(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Array getArray(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Object getObject(
		final String columnLabel,
		final java.util.Map<String,Class<?>> map
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Ref getRef(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Blob getBlob(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Clob getClob(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Array getArray(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Date getDate(final String columnLabel, final Calendar cal) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Time getTime(final String columnLabel, final Calendar cal) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Timestamp getTimestamp(
		final int columnIndex,
		final Calendar cal
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.sql.Timestamp getTimestamp(
		final String columnLabel,
		final Calendar cal
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.net.URL getURL(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.net.URL getURL(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateRef(final int columnIndex, final java.sql.Ref x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateRef(final String columnLabel, final java.sql.Ref x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBlob(final int columnIndex, final java.sql.Blob x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBlob(final String columnLabel, final java.sql.Blob x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateClob(final int columnIndex, final java.sql.Clob x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateClob(final String columnLabel, final java.sql.Clob x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateArray(final int columnIndex, final java.sql.Array x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateArray(final String columnLabel, final java.sql.Array x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public RowId getRowId(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public RowId getRowId(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateRowId(final String columnLabel, final RowId x) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNString(final int columnIndex, final String nString) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNString(final String columnLabel, final String nString) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException {
		throw notImplemented();
	}

	@Override
	public NClob getNClob(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public NClob getNClob(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public SQLXML getSQLXML(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public SQLXML getSQLXML(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getNString(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getNString(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.Reader getNCharacterStream(final int columnIndex) throws SQLException {
		throw notImplemented();
	}

	@Override
	public java.io.Reader getNCharacterStream(final String columnLabel) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNCharacterStream(
		final int columnIndex,
		final java.io.Reader x,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNCharacterStream(
		final String columnLabel,
		final java.io.Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateAsciiStream(
		final int columnIndex,
		final java.io.InputStream x,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBinaryStream(
		final int columnIndex,
		final java.io.InputStream x,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateCharacterStream(
		final int columnIndex,
		final java.io.Reader x,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateAsciiStream(
		final String columnLabel,
		final java.io.InputStream x,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBinaryStream(
		final String columnLabel,
		final java.io.InputStream x,
		final long length) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateCharacterStream(
		final String columnLabel,
		final java.io.Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBlob(
		final int columnIndex,
		final InputStream inputStream,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBlob(
		final String columnLabel,
		final InputStream inputStream,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateClob(
		final int columnIndex,
		final Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateClob(
		final String columnLabel,
		final Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNClob(
		final int columnIndex,
		final Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNClob(
		final String columnLabel,
		final Reader reader,
		final long length
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNCharacterStream(
		final int columnIndex,
		final java.io.Reader x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNCharacterStream(
		final String columnLabel,
		final java.io.Reader reader
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateAsciiStream(
		final int columnIndex,
		final java.io.InputStream x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBinaryStream(
		final int columnIndex,
		final java.io.InputStream x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateCharacterStream(
		final int columnIndex,
		final java.io.Reader x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateAsciiStream(
		final String columnLabel,
		final java.io.InputStream x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBinaryStream(
		final String columnLabel,
		final java.io.InputStream x
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateCharacterStream(
		final String columnLabel,
		final java.io.Reader reader
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBlob(
		final int columnIndex,
		final InputStream inputStream
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateBlob(
		final String columnLabel,
		final InputStream inputStream
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
		throw notImplemented();
	}

	@Override
	public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
		throw notImplemented();
	}

	@Override
	public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException {
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

