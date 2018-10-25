
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


import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.IndexMetadata;


public class DefaultDatabaseMetaData extends AbstractDatabaseMetaData {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		DefaultDatabaseMetaData.class.getName()
	);

	private final Driver.Context context;
	private final Cluster cluster;
	private final Session session;
	private final String keyspaceName;
	private final DefaultConnection connection;


	DefaultDatabaseMetaData(
		final Driver.Context context,
		final Cluster cluster,
		final Session session,
		final String keyspaceName,
		final DefaultConnection connection
	) {
		this.context = context;
		this.cluster = cluster;
		this.session = session;
		this.keyspaceName = keyspaceName;
		this.connection = connection;
	}


	@Override
	public String getDatabaseProductName() throws SQLException {
		return "cassandra";
	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		return "unknown";
	}

	@Override
	public String getDriverName() throws SQLException {
		return "jdbc_over_datastax";
	}

	@Override
	public String getDriverVersion() throws SQLException {
		return "1.0/datastax: " + cluster.getDriverVersion();
	}

	@Override
	public int getDriverMajorVersion() {
		return 1;
	}

	@Override
	public int getDriverMinorVersion() {
		return 0;
	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		// XXX fake
		return 1;
	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		// XXX fake
		return 0;
	}


	protected String getEffectiveKeyspaceName(final String schemaPattern) {
		final String result = (
			keyspaceName != null?
			keyspaceName:
			schemaPattern
		);

		return result;
	}


	@Override
	public ResultSet getTables(
		String catalog,
		String schemaPattern,
		String tableNamePattern,
		String types[]
	) throws SQLException {

		logger.finest("getTables()");
		logger.finest("\ncatalog: [" + catalog + "]");
		logger.finest("\nschemaPattern: [" + schemaPattern + "]");
		logger.finest("\ntableNamePattern: [" + tableNamePattern + "]");

		if (types != null) {
			for (String type: types)
				logger.info("\nfinest: [" + type + "]");
		}

		final String effectiveKeyspaceName = getEffectiveKeyspaceName(schemaPattern);

		final KeyspaceMetadata keyspaceMetadata = cluster.getMetadata().getKeyspace(effectiveKeyspaceName);

		final Collection<TableMetadata> tablesMetadata = keyspaceMetadata.getTables();

		final List<Object[]> list = new ArrayList<>();

		for (TableMetadata tableMetadata: tablesMetadata) {

			final String tableCatalog = null;
			final String tableSchema = (keyspaceName != null? null: effectiveKeyspaceName);
			final String tableName = tableMetadata.getName();
			final String tableType = "TABLE";
			final String remarks = null;
			final String typeCatalog = null;
			final String typeSchema = null;
			final String typeName = null;
			final String selfReferencingColumnName = null;
			final String refGeneration = null;

			final Object[] row = new Object[10];

			row[0] = tableCatalog;
			row[1] = tableSchema;
			row[2] = tableName;
			row[3] = tableType;
			row[4] = remarks;
			row[5] = typeCatalog;
			row[6] = typeSchema;
			row[7] = typeName;
			row[8] = selfReferencingColumnName;
			row[9] = refGeneration;

			if ((types == null) || includes(types, tableType))
				list.add(row);
		}

		final String[] names = {
			"TABLE_CAT",
			"TABLE_SCHEM",
			"TABLE_NAME",
			"TABLE_TYPE",
			"REMARKS",
			"TYPE_CAT",
			"TYPE_SCHEM",
			"TYPE_NAME",
			"SELF_REFERENCING_COL_NAME",
			"REF_GENERATION"
		};

		return new DefaultResultSet(names, list);

	//	throw new UnsupportedOperationException("not implemented yet");
	}

	protected boolean includes(final String[] list, final String element) {

		for (String item: list) {
			if ((item == null) && (element == null))
				return true;
			if ((item == null) || (element == null))
				continue;
			if (item.equals(element))
				return true;
		}

		return false;
	}


	@Override
	public ResultSet getColumns(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern,
		final String columnNamePattern
	) throws SQLException {

		logger.finest(
			"getColumns(" + catalog + ", " +
			schemaPattern + ", " + tableNamePattern + ", " +
			columnNamePattern + ")"
		);


		final String effectiveKeyspaceName = getEffectiveKeyspaceName(schemaPattern);

		final KeyspaceMetadata keyspaceMetadata = cluster.getMetadata().getKeyspace(effectiveKeyspaceName);

		final Collection<TableMetadata> tablesMetadata = keyspaceMetadata.getTables();

		final List<Object[]> list = new ArrayList<>();

		for (TableMetadata tableMetadata: tablesMetadata) {

			final int columnPosition = 0;

			for (ColumnMetadata columnMetadata: tableMetadata.getColumns()) {

				final String tableCatalog = null;
				final String tableSchema = (keyspaceName != null? null: effectiveKeyspaceName);
				final String tableName = tableMetadata.getName();

				final String columnName = columnMetadata.getName();
				final Integer dataType = context.typeFromCassandraToSQL(columnMetadata.getType());
				final String typeName = columnMetadata.getType().getName().toString();
				final Integer columnSize = null;
				final Integer bufferLength = null;
				final Integer decimalDigits = null;
				final Integer numPrecRadix = 10;
				final Integer nullable = columnNullableUnknown;
				final String remarks = null;
				final String columnDef = null;
				final Integer sqlDataType = null;
				final Integer sqlDatetimeSub = null;
				final Integer charOctetLength = Integer.MAX_VALUE;
				final Integer ordinalPosition = (columnPosition + 1);
				final String isNullable = "";
				final String scopeCatalog = null;
				final String scopeSchema = null;
				final String scopeTable = null;
				final Short sourceDataType = null;
				final String isAutoincrement = "";
				final String isGeneratedColumn = "";

				final Object[] row = new Object[24];

				row[0] = tableCatalog;
				row[1] = tableSchema;
				row[2] = tableName;

				row[3] = columnName;
				row[4] = dataType;
				row[5] = typeName;
				row[6] = columnSize;
				row[7] = bufferLength;
				row[8] = decimalDigits;
				row[9] = numPrecRadix;
				row[10] = nullable;
				row[11] = remarks;
				row[12] = columnDef;
				row[13] = sqlDataType;
				row[14] = sqlDatetimeSub;
				row[15] = charOctetLength;
				row[16] = ordinalPosition;
				row[17] = isNullable;
				row[18] = scopeCatalog;
				row[19] = scopeSchema;
				row[20] = scopeTable;
				row[21] = sourceDataType;
				row[22] = isAutoincrement;
				row[23] = isGeneratedColumn;

				list.add(row);
			}
		}

		final String[] names = {
			"TABLE_CAT",
			"TABLE_SCHEM",
			"TABLE_NAME",

			"COLUMN_NAME",
			"DATA_TYPE",
			"TYPE_NAME",
			"COLUMN_SIZE",
			"BUFFER_LENGTH",
			"DECIMAL_DIGITS",
			"NUM_PREC_RADIX",
			"NULLABLE",
			"REMARKS",
			"COLUMN_DEF",
			"SQL_DATA_TYPE",
			"SQL_DATETIME_SUB",
			"CHAR_OCTET_LENGTH",
			"ORDINAL_POSITION",
			"IS_NULLABLE",
			"SCOPE_CATALOG",
			"SCOPE_SCHEMA",
			"SCOPE_TABLE",
			"SOURCE_DATA_TYPE",
			"IS_AUTOINCREMENT",
			"IS_GENERATEDCOLUMN"
		};

		return new DefaultResultSet(names, list);
	}

	@Override
	public ResultSet getImportedKeys(
		String catalog, String schema,
		String table
	) throws SQLException {
		logger.finest("getImportedKeys(" + catalog + ", " + schema + ", " + table + ")");
		return new DefaultResultSet(new String[0], new ArrayList<>());
	}


	@Override
	public ResultSet getIndexInfo(
		final String catalog,
		final String schema,
		final String table,
		final boolean unique,
		final boolean approximate
	) throws SQLException {

		logger.finest(
			"getIndexInfo(" + catalog + ", " + schema + ", " +
			table + ", " + unique + ", " + approximate + ")"
		);

		final String effectiveKeyspaceName = getEffectiveKeyspaceName(schema);

		final KeyspaceMetadata keyspaceMetadata = cluster.getMetadata().getKeyspace(effectiveKeyspaceName);

		final Collection<TableMetadata> tablesMetadata = keyspaceMetadata.getTables();

		final List<Object[]> list = new ArrayList<>();

		for (TableMetadata tableMetadata: tablesMetadata) {

			if ((table != null) && (!table.equals(tableMetadata.getName())))
				continue;

			final String tableCatalog = null;
			final String tableSchema = (keyspaceName != null? null: effectiveKeyspaceName);
			final String tableName = tableMetadata.getName();

			for (IndexMetadata indexMetadata: tableMetadata.getIndexes()) {

				final boolean nonUnique = false;
				final String indexQualifier = null;
				final String indexName = indexMetadata.getName();
				final short type = tableIndexOther;
				final short ordinalPosition = 0;
				final String columnName  = indexMetadata.getTarget();
				final String ascOrDesc = null; //"A";
				final long cardinality = 0;
				final long pages = 0;
				final String filterCondition = null;

				final Object[] row = new Object[13];

				logger.finest("index[" + indexName + "]: targetName: [" + indexMetadata.getTarget() + "]");

				row[0] = tableCatalog;
				row[1] = tableSchema;
				row[2] = tableName;

				row[3] = nonUnique;
				row[4] = indexQualifier;
				row[5] = indexName;
				row[6] = type;
				row[7] = ordinalPosition;
				row[8] = columnName;
				row[9] = ascOrDesc;
				row[10] = cardinality;
				row[11] = pages;
				row[12] = filterCondition;

				list.add(row);
			}
		}

		final String[] names = {
			"TABLE_CAT",
			"TABLE_SCHEM",
			"TABLE_NAME",

			"NON_UNIQUE",
			"INDEX_QUALIFIER",
			"INDEX_NAME",
			"TYPE",
			"ORDINAL_POSITION",
			"COLUMN_NAME",
			"ASC_OR_DESC",
			"CARDINALITY",
			"PAGES",
			"FILTER_CONDITION"
		};

		return new DefaultResultSet(names, list);
	}

	@Override
	public boolean storesUpperCaseIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesLowerCaseIdentifiers() throws SQLException {
		return true;
	}

	@Override
	public boolean storesMixedCaseIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return connection;
	}

	@Override
	public boolean supportsNamedParameters() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsResultSetType(final int type) throws SQLException {
		switch (type) {
			case ResultSet.TYPE_FORWARD_ONLY: return true;
			default: return false;
		}
	}

	@Override
	public boolean supportsGetGeneratedKeys() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsBatchUpdates() throws SQLException {
		return true;
	}

	@Override
	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		return false;
	}

	@Override
	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		return false;
	}

	private final List<String> sqlKeywordsList = Arrays.asList(
		"ADD",	 	//	yes
		"AGGREGATE",	// 	yes
		"ALL",		// 	no
		"ALLOW",	//	yes
		"ALTER",	//	yes
		"AND",		//	yes
		"ANY",		//	yes
		"APPLY",	//	yes
		"AS",		//	no
		"ASC",		//	yes
		"ASCII",	//	no
		"AUTHORIZE",	//	yes
		"BATCH",	//	yes
		"BEGIN",	//	yes
		"BIGINT",	//	no
		"BLOB",		//	no
		"BOOLEAN",	//	no
		"BY",		//	yes
		"CLUSTERING",	//	no
		"COLUMNFAMILY",	//	yes
		"COMPACT",	//	no
		"CONSISTENCY",	//	no
		"COUNT",	//	no
		"COUNTER",	//	no
		"CREATE",	//	yes
		"CUSTOM",	//	no
		"DECIMAL",	//	no
		"DELETE",	//	yes
		"DESC",		//	yes
		"DISTINCT",	//	no
		"DOUBLE",	//	no
		"DROP",		//	yes
		"EACH_QUORUM",	//	yes
		"ENTRIES",	//	yes
		"EXISTS",	//	no
		"FILTERING",	//	no
		"FLOAT",	//	no
		"FROM",		//	yes
		"FROZEN",	//	no
		"FULL",		//	yes
		"GRANT",	//	yes
		"IF",		//	yes
		"IN",		//	yes
		"INDEX",	//	yes
		"INET",		//	yes
		"INFINITY",	//	yes
		"INSERT",	//	yes
		"INT",		//	no
		"INTO",		//	yes
		"KEY",		//	no
		"KEYSPACE",	//	yes
		"KEYSPACES",	//	yes
		"LEVEL",	//	no
		"LIMIT",	//	yes
		"LIST",		//	no
		"LOCAL_ONE",	//	yes
		"LOCAL_QUORUM",	//	yes
		"MAP",		//	no
		"MATERIALIZED",	//	yes
		"MODIFY",	//	yes
		"NAN",		//	yes
		"NORECURSIVE",	//	yes
		"NOSUPERUSER",	//	no
		"NOT",		//	yes
		"OF",		//	yes
		"ON",		//	yes
		"ONE",		//	yes
		"ORDER",	//	yes
		"PARTITION",	//	yes
		"PASSWORD",	//	yes
		"PER",		//	yes
		"PERMISSION",	//	no
		"PERMISSIONS",	//	no
		"PRIMARY",	//	yes
		"QUORUM",	//	yes
		"RENAME",	//	yes
		"REVOKE",	//	yes
		"SCHEMA",	//	yes
		"SELECT",	//	yes
		"SET",		//	yes
		"STATIC",	//	no
		"STORAGE",	//	no
		"SUPERUSER",	//	no
		"TABLE",	//	yes
		"TEXT",		//	no
		"TIME",		//	yes
		"TIMESTAMP",	//	no
		"TIMEUUID",	//	no
		"THREE",	//	yes
		"TO",		//	yes
		"TOKEN",	//	yes
		"TRUNCATE",	//	yes
		"TTL",		//	no
		"TUPLE",	//	no
		"TWO",		//	yes
		"TYPE",		//	no
		"UNLOGGED",	//	yes
		"UPDATE",	//	yes
		"USE",		//	yes
		"USER",		//	no
		"USERS",	//	no
		"USING",	//	yes
		"UUID",		//	no
		"VALUES",	//	no
		"VARCHAR",	//	no
		"VARINT",	//	no
		"VIEW",		//	yes
		"WHERE",	//	yes
		"WITH",		//	yes
		"WRITETIME"	//	no
	);

	private final String sqlKeywords = String.join(",", sqlKeywordsList);

	@Override
	public String getSQLKeywords() throws SQLException {
		return sqlKeywords;
	}

	@Override
	public int getSQLStateType() throws SQLException {
		return sqlStateSQL; // XXX whatever
	}

	@Override
	public boolean locatorsUpdateCopy() throws SQLException {
		return true;	// XXX whatever
	}

	@Override
	public ResultSet getTypeInfo() throws SQLException {
		return new DefaultResultSet(new String[0], new ArrayList<>()); // XXX whatever
	}

	@Override
	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		return false;
	}

	@Override
	public String getCatalogSeparator() throws SQLException {
		return ".";
	}

	@Override
	public boolean isCatalogAtStart() throws SQLException {
		return true;
	}

	@Override
	public int getJDBCMajorVersion() throws SQLException {
		return 4;
	}
	@Override
	public int getJDBCMinorVersion() throws SQLException {
		return 3;
	}


}

