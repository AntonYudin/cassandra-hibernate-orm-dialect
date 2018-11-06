
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
		return getSessionInformation().getDatabaseProductName();
	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		return getSessionInformation().getDatabaseProductVersion();
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

	private SessionInformation sessionInformation;

	protected SessionInformation getSessionInformation() {
		if (sessionInformation == null)
			sessionInformation = new SessionInformation(session);
		return sessionInformation;
	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		return getSessionInformation().getDatabaseMajorVersion();
	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		return getSessionInformation().getDatabaseMinorVersion();
	}


	protected String getEffectiveKeyspaceName(final String schema) {
		final String result = (
			schema != null?
			schema:
			keyspaceName
		);

		return result;
	}


	@Override
	public ResultSet getTables(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern,
		final String types[]
	) throws SQLException {

		logger.info("getTables()");
		logger.info("\ncatalog: [" + catalog + "]");
		logger.info("\nschemaPattern: [" + schemaPattern + "]");
		logger.info("\ntableNamePattern: [" + tableNamePattern + "]");

		if (types != null) {
			for (String type: types)
				logger.info("\ntype: [" + type + "]");
		}

		final String effectiveKeyspaceName = getEffectiveKeyspaceName(schemaPattern);

		final KeyspaceMetadata keyspaceMetadata = cluster.getMetadata().getKeyspace(
			effectiveKeyspaceName
		);

		final Collection<TableMetadata> tablesMetadata = keyspaceMetadata.getTables();

		final List<Object[]> list = new ArrayList<>();

		for (TableMetadata tableMetadata: tablesMetadata) {

			final String tableCatalog = getClusterName();
			final String tableSchema = (
				keyspaceName != null? keyspaceName: effectiveKeyspaceName
			);
			final String tableName = tableMetadata.getName();
			final String tableType = "TABLE";
			final String remarks = tableMetadata.exportAsString();
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

		final KeyspaceMetadata keyspaceMetadata = cluster.getMetadata().getKeyspace(
			effectiveKeyspaceName
		);

		final Collection<TableMetadata> tablesMetadata = keyspaceMetadata.getTables();

		final List<Object[]> list = new ArrayList<>();

		for (TableMetadata tableMetadata: tablesMetadata) {

			final int columnPosition = 0;

			final String tableName = tableMetadata.getName();

			if (
				(tableNamePattern != null) &&
				(!tableName.equalsIgnoreCase(tableNamePattern))
			)
				continue;

			for (ColumnMetadata columnMetadata: tableMetadata.getColumns()) {

				final String tableCatalog = getClusterName();

				final String tableSchema = (
					keyspaceName != null? keyspaceName: effectiveKeyspaceName
				);

				final String columnName = columnMetadata.getName();
				final Integer dataType = context.typeFromCassandraToSQL(
					columnMetadata.getType()
				);

				//final String typeName = columnMetadata.getType().getName().toString();
				final String typeName = columnMetadata.getType().asFunctionParameterString();

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

				row[0] = (String) tableCatalog;
				row[1] = (String) tableSchema;
				row[2] = (String) tableName;

				row[3] = (String) columnName;
				row[4] = (Integer) dataType;
				row[5] = (String) typeName;

				row[6] = (Integer) columnSize;
				row[7] = bufferLength; // is not used
				row[8] = (Integer) decimalDigits;
				row[9] = (Integer) numPrecRadix;
				row[10] = (Integer) nullable;
				row[11] = (String) remarks;
				row[12] = (String) columnDef;
				row[13] = (Integer) sqlDataType;
				row[14] = (Integer) sqlDatetimeSub;
				row[15] = (Integer) charOctetLength;
				row[16] = (Integer) ordinalPosition;
				row[17] = (String) isNullable;
				row[18] = (String) scopeCatalog;
				row[19] = (String) scopeSchema;
				row[20] = (String) scopeTable;
				row[21] = (Short) sourceDataType;
				row[22] = (String) isAutoincrement;
				row[23] = (String) isGeneratedColumn;

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
		//	"DATA_TYPE",
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

		final KeyspaceMetadata keyspaceMetadata = cluster.getMetadata().getKeyspace(
			effectiveKeyspaceName
		);

		final Collection<TableMetadata> tablesMetadata = keyspaceMetadata.getTables();

		final List<Object[]> list = new ArrayList<>();

		for (TableMetadata tableMetadata: tablesMetadata) {

			if ((table != null) && (!table.equals(tableMetadata.getName())))
				continue;

			final String tableCatalog = getClusterName();
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
	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSavepoints() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public boolean supportsSchemasInDataManipulation() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public boolean supportsCatalogsInDataManipulation() throws SQLException {
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

	// XXX not used by hibernate
	@Override
	public boolean supportsTransactions() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public String getIdentifierQuoteString() throws SQLException {
		return "\"";
	}

	// XXX not used by hibernate
	@Override
	public String getCatalogTerm() throws SQLException {
		return "catalog";
	}

	// XXX not used by hibernate
	@Override
	public String getSchemaTerm() throws SQLException {
		return "keyspace";
	}

	// XXX not used by hibernate
	@Override
	public String getProcedureTerm() throws SQLException {
		return "procedure";
	}

	// XXX not used by hibernate
	@Override
	public String getNumericFunctions() throws SQLException {
		return "";
	}

	// XXX not used by hibernate
	@Override
	public String getStringFunctions() throws SQLException {
		return "";
	}

	// XXX not used by hibernate
	@Override
	public String getTimeDateFunctions() throws SQLException {
		return "";
	}

	private String clusterName = null;

	protected String getClusterName() {

		if (clusterName == null) {

			final com.datastax.driver.core.ResultSet resultSet = session.execute(
				"select cluster_name, data_center from system.local"
			);


			final java.util.Iterator<com.datastax.driver.core.Row> iterator =
				resultSet.iterator()
			;

			while (iterator.hasNext()) {

				final com.datastax.driver.core.Row row = iterator.next();

				final String clusterNameColumn = row.getString(0);
				final String dataCenterColumn = row.getString(1);

				clusterName = clusterNameColumn + "/" + dataCenterColumn;

				break;
			}
		}

		return (clusterName != null? clusterName: cluster.getClusterName());
	}


	// XXX not used by hibernate
	@Override
	public ResultSet getCatalogs() throws SQLException {

		final List<Object[]> list = new ArrayList<>();

		//list.add(new Object[0]);
		list.add(new Object[] {getClusterName()});

		final String[] names = {
			"TABLE_CAT"
		};

		return new DefaultResultSet(names, list);
	}


	// XXX not used by hibernate
	@Override
	public ResultSet getSchemas() throws SQLException {

		final List<Object[]> list = new ArrayList<>();


		final com.datastax.driver.core.ResultSet resultSet = session.execute(
			"select keyspace_name from system_schema.keyspaces"
		);


		final java.util.Iterator<com.datastax.driver.core.Row> iterator =
			resultSet.iterator()
		;

		while (iterator.hasNext()) {

			final String name = iterator.next().getString(0);

			list.add(new Object[] {name, getClusterName()});
		}

		/*
		list.add(new Object[] {"system", null});
		list.add(new Object[] {"system_auth", null});
		list.add(new Object[] {"system_distributed", null});
		list.add(new Object[] {"system_schema", null});
		list.add(new Object[] {"system_traces", null});
		*/

		final String[] names = {
			"TABLE_SCHEM",
			"TABLE_CATALOG"
		};

		return new DefaultResultSet(names, list);
	}


	// XXX not used by hibernate
	@Override
	public ResultSet getTableTypes() throws SQLException {

		final List<Object[]> list = new ArrayList<>();

		list.add(new Object[] {"TABLE"});

		final String[] names = {
			"TABLE_TYPE"
		};

		return new DefaultResultSet(names, list);
	}


	// XXX not used by hibernate
	@Override
	public ResultSet getPrimaryKeys(
		final String catalog,
		final String schema,
		final String table
	) throws SQLException {

		logger.finest(
			"getPrimaryKeys(" + catalog + ", " +
			schema + ", " + table + ")"
		);

		final String effectiveKeyspaceName = getEffectiveKeyspaceName(schema);

		final KeyspaceMetadata keyspaceMetadata = cluster.getMetadata().getKeyspace(
			effectiveKeyspaceName
		);

		final Collection<TableMetadata> tablesMetadata = keyspaceMetadata.getTables();

		final List<Object[]> list = new ArrayList<>();

		for (TableMetadata tableMetadata: tablesMetadata) {

			final int columnPosition = 0;

			final String tableName = tableMetadata.getName();

			if (
				(table != null) &&
				(!tableName.equalsIgnoreCase(table))
			)
				continue;

			int i = 0;

			for (ColumnMetadata columnMetadata: tableMetadata.getPrimaryKey()) {

				final String tableCatalog = getClusterName();

				final String tableSchema = (
					keyspaceName != null? keyspaceName: effectiveKeyspaceName
				);

				final String columnName = columnMetadata.getName();

				final Integer keySeq = i + 1;
				final String pkName = null;

				final Object[] row = new Object[6];

				row[0] = (String) tableCatalog;
				row[1] = (String) tableSchema;
				row[2] = (String) tableName;

				row[3] = (String) columnName;

				row[4] = (Integer) keySeq;
				row[5] = (String) pkName;

				list.add(row);

				i++;
			}
		}

		final String[] names = {
			"TABLE_CAT",
			"TABLE_SCHEM",
			"TABLE_NAME",
			"COLUMN_NAME",
			"KEY_SEQ",
			"PK_NAME"
		};

		return new DefaultResultSet(names, list);
	}


	public static class SessionInformation implements java.io.Serializable {

		public SessionInformation(final Session session) {

			try {
				final com.datastax.driver.core.ResultSet resultSet = session.execute(
					"select release_version from system.local"
				);

				final com.datastax.driver.core.Row row = resultSet.iterator().next();

				int databaseMajorVersion = -1;
				int databaseMinorVersion = -1;

				String version = null;

				if (row != null) {

					version = row.getString(0);

					if (version != null) {

						final String[] parts = version.split("\\.");

						if (parts.length > 0)
							databaseMajorVersion = Integer.valueOf(parts[0]);

						if (parts.length > 1)
							databaseMinorVersion = Integer.valueOf(parts[1]);
					}
				}

				this.databaseMajorVersion = databaseMajorVersion;
				this.databaseMinorVersion = databaseMinorVersion;
				this.databaseProductVersion = version;
				this.databaseProductName = "Cassandra";

			} catch (java.lang.Exception exception) {
				logger.log(java.util.logging.Level.SEVERE, "exception", exception);
				throw exception;
			}
		}


		private final int databaseMajorVersion;

		public int getDatabaseMajorVersion() throws SQLException {
			return databaseMajorVersion;
		}

		public final int databaseMinorVersion;

		public int getDatabaseMinorVersion() throws SQLException {
			return databaseMinorVersion;
		}

		private final String databaseProductName;

		public String getDatabaseProductName() {
			return databaseProductName;
		}

		private final String databaseProductVersion;

		public String getDatabaseProductVersion() {
			return databaseProductVersion;
		}
	}


	// XXX not used by hibernate
	@Override
	public int getDefaultTransactionIsolation() throws SQLException {
		return Connection.TRANSACTION_NONE;
	}

	// XXX not used by hibernate
	@Override
	public boolean isReadOnly() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public boolean supportsStoredProcedures() throws SQLException {
		return false;
	}

	// XXX not used by hibernate
	@Override
	public ResultSet getSuperTables(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern
	) throws SQLException {

		final List<Object[]> list = new ArrayList<>();

//		list.add(new Object[0]);

		final String[] names = {
			"TABLE_CAT",
			"TABLE_SCHEM",
			"TABLE_NAME",
			"SUPERTABLE_NAME"
		};

		return new DefaultResultSet(names, list);
	}

	// XXX not used by hibernate
	@Override
	public ResultSet getProcedures(
		final String catalog,
		final String schemaPattern,
		final String procedureNamePattern
	) throws SQLException {

		final List<Object[]> list = new ArrayList<>();

//		list.add(new Object[0]);

		final String[] names = {
			"PROCEDURE_CAT",
			"PROCEDURE_SCHEM",
			"PROCEDURE_NAME",
			"",
			"",
			"",
			"REMARKS",
			"PROCEDURE_TYPE",
			"SPECIFIC_NAME"
		};

		return new DefaultResultSet(names, list);
	}

	// XXX not used by hibernate
	@Override
	public ResultSet getUDTs(
		final String catalog,
		final String schemaPattern,
		final String typeNamePattern,
		final int[] types
	) throws SQLException {

		final List<Object[]> list = new ArrayList<>();

//		list.add(new Object[0]);

		final String[] names = {
			"TYPE_CAT",
			"TYPE_SCHEM",
			"TYPE_NAME",
			"CLASS_NAME",
			"DATA_TYPE",
			"REMARKS",
			"BASE_TYPE"
		};

		return new DefaultResultSet(names, list);
	}


	// XXX not used by hibernate
	@Override
	public String getURL() throws SQLException {
		return connection.getConnectionString().getURL();
	}


	// XXX not used by hibernate
	@Override
	public boolean allProceduresAreCallable() throws SQLException {
		return true;
	}


	// XXX not used by hibernate
	@Override
	public boolean allTablesAreSelectable() throws SQLException {
		return true;
	}


	// XXX not used by hibernate
	@Override
	public boolean supportsColumnAliasing() throws SQLException {
		return true;
	}


	// XXX not used by hibernate
	@Override
	public boolean supportsTableCorrelationNames() throws SQLException {
		return false;
	}


	// XXX not used by hibernate
	@Override
	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		return false;
	}



}

