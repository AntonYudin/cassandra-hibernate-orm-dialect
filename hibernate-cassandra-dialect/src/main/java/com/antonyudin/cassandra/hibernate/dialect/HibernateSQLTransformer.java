
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
 
package com.antonyudin.cassandra.hibernate.dialect;


public class HibernateSQLTransformer implements com.antonyudin.cassandra.driver.SQLTransformer {

	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		HibernateSQLTransformer.class.getName()
	);

	private final static boolean logTransformation = false;

	private final static String patternSelect = "select ";
	private final static String patternFrom = " from ";
	private final static String patternNotNull = " not null";
	private final static String patternAlterTable = "alter table ";
	private final static String patternAddColumn = "add column ";
	private final static String patternAddConstraint = "add constraint ";
	private final static String patternDropConstraint = "drop constraint ";


	public HibernateSQLTransformer() {
		logger.info("HibernateSQLTransformer created.");
	}

	@Override
	public String transform(final String originalSQLNoTrim) {

		log("transform(" + originalSQLNoTrim + ")");

		final String originalSQL = originalSQLNoTrim.trim();

		final int indexOfFirstQuote = originalSQL.indexOf("'");
		final int limit = (indexOfFirstQuote > 0? indexOfFirstQuote: originalSQL.length());


		if (originalSQL.startsWith(patternSelect)) {

			// for hibernate select, remove table aliases.
			// Cassandra does not support table aliases.

			final int indexOfFrom = originalSQL.indexOf(" from ");

			log("indexOfFrom: [" + indexOfFrom + "]");

			if (indexOfFrom < 0)
				return originalSQL;

			final int indexOfSpace0 = originalSQL.indexOf(
				" ", indexOfFrom + patternFrom.length()
			);

			log("indexOfSpace0: [" + indexOfSpace0 + "]");

			int indexOfSpace1 = originalSQL.indexOf(" ", indexOfSpace0 + 1);

			log("indexOfSpace1: [" + indexOfSpace1 + "]");

			if (indexOfSpace1 < 0)
				indexOfSpace1 = originalSQL.length();

			log("indexOfSpace1: [" + indexOfSpace1 + "]");

			final String tableName = originalSQL.substring(
				indexOfFrom + patternFrom.length(), indexOfSpace0
			);

			log("tableName: [" + tableName + "]");

			final String aliasName = originalSQL.substring(
				indexOfSpace0 + 1, indexOfSpace1
			).trim();

		//	logger.info("tableName: [" + tableName + "]");
		//	logger.info("aliasName: [" + aliasName + "]");

			if (aliasName.length() > 0) {

				final String noAlias = originalSQL.replaceAll(" " + aliasName + "\\.", " ");
				final String noAlias2 = noAlias.replaceAll("\\(" + aliasName + "\\.", "(");
				final String result = noAlias2.replaceAll(" from " + tableName + " " + aliasName, " from " + tableName);

				log("transformed sql: [" + result + "]");

				return result;
			}

		} else if (originalSQL.startsWith("create table")) {

			// remove "NOT NULL"
			// Cassandra does not support "NOT NULL"

		//	logger.info("create table");

			final StringBuilder builder = new StringBuilder(originalSQL);

			for (;;) {
				final int notNullIndex = builder.indexOf(patternNotNull);

				if ((notNullIndex < 0) || (notNullIndex >= limit))
					break;

				builder.delete(notNullIndex, notNullIndex + patternNotNull.length());
			}

			final String result = builder.toString();

			log("transformed sql: [" + result + "]");

			return result;

		} else if (originalSQL.startsWith(patternAlterTable)) {


			// for alter table
			// 1. change "add column" to "add"
			// 2. remove "add constraint" query completely
			// 3. remove "drop constraint" query completely

			final StringBuilder builder = new StringBuilder(originalSQL);

			final int indexOfEndOfTableName = builder.indexOf(" ", patternAlterTable.length());

		//	logger.info("indexOfEndOfTableName: [" + indexOfEndOfTableName + "]");

			if (indexOfEndOfTableName > 0) {

				final int indexOfAddColumn = builder.indexOf(patternAddColumn, indexOfEndOfTableName);

		//		logger.info("indexOfAddColumn: [" + indexOfAddColumn + "]");

				if (indexOfAddColumn > 0) {
					builder.delete(indexOfAddColumn, indexOfAddColumn + patternAddColumn.length());
					builder.insert(indexOfAddColumn, "add ");
				} else {

					final int indexOfAddConstraint = builder.indexOf(patternAddConstraint, indexOfEndOfTableName);
					final int indexOfDropConstraint = builder.indexOf(patternDropConstraint, indexOfEndOfTableName);

		//			logger.info("indexOfAddConstraint: [" + indexOfAddConstraint + "]");

					if ((indexOfAddConstraint > 0) || (indexOfDropConstraint > 0))
						builder.delete(0, builder.length());
				}

				final String result = builder.toString();

				log("transformed sql: [" + result + "]");

				return result;
			}
		}

		return originalSQLNoTrim;
	}

	protected void log(final String message) {
		if (logTransformation)
			logger.info(message);
	}

}

