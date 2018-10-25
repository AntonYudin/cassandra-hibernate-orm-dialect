
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


import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.Connection;


public abstract class AbstractDatabaseMetaData implements DatabaseMetaData {


	@Override
	public boolean allProceduresAreCallable() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean allTablesAreSelectable() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getURL() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getUserName() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean nullsAreSortedHigh() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean nullsAreSortedLow() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean nullsAreSortedAtStart() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean nullsAreSortedAtEnd() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getDatabaseProductName() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getDriverName() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getDriverVersion() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getDriverMajorVersion() {
		throw notImplemented();
	}

	@Override
	public int getDriverMinorVersion() {
		throw notImplemented();
	}

	@Override
	public boolean usesLocalFiles() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean usesLocalFilePerTable() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean storesUpperCaseIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean storesLowerCaseIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean storesMixedCaseIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getIdentifierQuoteString() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getSQLKeywords() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getNumericFunctions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getStringFunctions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getSystemFunctions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getTimeDateFunctions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getSearchStringEscape() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getExtraNameCharacters() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsColumnAliasing() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean nullPlusNonNullIsNull() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsConvert() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsConvert(final int fromType, final int toType) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsTableCorrelationNames() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsExpressionsInOrderBy() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsOrderByUnrelated() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsGroupBy() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsGroupByUnrelated() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsGroupByBeyondSelect() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsLikeEscapeClause() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsMultipleResultSets() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsMultipleTransactions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsNonNullableColumns() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsMinimumSQLGrammar() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsCoreSQLGrammar() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsExtendedSQLGrammar() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsANSI92FullSQL() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsOuterJoins() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsFullOuterJoins() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsLimitedOuterJoins() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getSchemaTerm() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getProcedureTerm() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getCatalogTerm() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean isCatalogAtStart() throws SQLException {
		throw notImplemented();
	}

	@Override
	public String getCatalogSeparator() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSchemasInDataManipulation() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsPositionedDelete() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsPositionedUpdate() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSelectForUpdate() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsStoredProcedures() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSubqueriesInComparisons() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSubqueriesInExists() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSubqueriesInIns() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsCorrelatedSubqueries() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsUnion() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsUnionAll() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxBinaryLiteralLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxCharLiteralLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxColumnNameLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxColumnsInGroupBy() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxColumnsInIndex() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxColumnsInOrderBy() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxColumnsInSelect() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxColumnsInTable() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxConnections() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxCursorNameLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxIndexLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxSchemaNameLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxProcedureNameLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxCatalogNameLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxRowSize() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxStatementLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxStatements() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxTableNameLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxTablesInSelect() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getMaxUserNameLength() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getDefaultTransactionIsolation() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsTransactions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsTransactionIsolationLevel(final int level) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getProcedures(
		final String catalog,
		final String schemaPattern,
		final String procedureNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getProcedureColumns(
		final String catalog,
		final String schemaPattern,
		final String procedureNamePattern,
		final String columnNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getTables(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern,
		final String types[]
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getSchemas() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getCatalogs() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getTableTypes() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getColumns(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern,
		final String columnNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getColumnPrivileges(
		final String catalog,
		final String schema,
		final String table,
		final String columnNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getTablePrivileges(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getBestRowIdentifier(
		final String catalog,
		final String schema,
		final String table,
		final int scope,
		final boolean nullable
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getVersionColumns(
		final String catalog,
		final String schema,
		final String table
	) throws SQLException {
		throw notImplemented();
	}


	@Override
	public ResultSet getPrimaryKeys(
		final String catalog,
		final String schema,
		final String table
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getImportedKeys(
		final String catalog,
		final String schema,
		final String table
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getExportedKeys(
		final String catalog,
		final String schema,
		final String table
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getCrossReference(
		final String parentCatalog,
		final String parentSchema,
		final String parentTable,
		final String foreignCatalog,
		final String foreignSchema,
		final String foreignTable
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getTypeInfo() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getIndexInfo(
		final String catalog,
		final String schema,
		final String table,
		final boolean unique,
		final boolean approximate
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsResultSetType(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsResultSetConcurrency(
		final int type, final int concurrency
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean ownUpdatesAreVisible(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean ownDeletesAreVisible(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean ownInsertsAreVisible(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean othersUpdatesAreVisible(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean othersDeletesAreVisible(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean othersInsertsAreVisible(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean updatesAreDetected(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean deletesAreDetected(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean insertsAreDetected(final int type) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsBatchUpdates() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getUDTs(
		final String catalog,
		final String schemaPattern,
		final String typeNamePattern,
		final int[] types
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public Connection getConnection() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsSavepoints() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsNamedParameters() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsMultipleOpenResults() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsGetGeneratedKeys() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getSuperTypes(
		final String catalog,
		final String schemaPattern,
		final String typeNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getSuperTables(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getAttributes(
		final String catalog,
		final String schemaPattern,
		final String typeNamePattern,
		final String attributeNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsResultSetHoldability(int holdability) throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getJDBCMajorVersion() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getJDBCMinorVersion() throws SQLException {
		throw notImplemented();
	}

	@Override
	public int getSQLStateType() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean locatorsUpdateCopy() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsStatementPooling() throws SQLException {
		throw notImplemented();
	}

	@Override
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getSchemas(final String catalog, final String schemaPattern) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getClientInfoProperties() throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getFunctions(
		final String catalog,
		final String schemaPattern,
		final String functionNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getFunctionColumns(
		final String catalog,
		final String schemaPattern,
		final String functionNamePattern,
		final String columnNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public ResultSet getPseudoColumns(
		final String catalog,
		final String schemaPattern,
		final String tableNamePattern,
		final String columnNamePattern
	) throws SQLException {
		throw notImplemented();
	}

	@Override
	public boolean generatedKeyAlwaysReturned() throws SQLException {
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
