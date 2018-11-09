
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

package com.antonyudin.cassandra.hibernate.dialect;


import java.sql.SQLException;
import java.sql.Types;
import java.sql.DatabaseMetaData;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


import java.util.UUID;
import java.util.List;

import org.hibernate.engine.jdbc.env.spi.AnsiSqlKeywords;
import org.hibernate.engine.jdbc.env.spi.IdentifierCaseStrategy;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelperBuilder;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;

import org.jboss.logging.Logger;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import org.hibernate.dialect.Dialect;


public class CassandraDialect extends Dialect {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		CassandraDialect.class.getName()
	);

	public CassandraDialect() {

		super();

		logger.fine(()-> "CassandraDialect() ...");

		registerColumnType(Types.DOUBLE, "double");
		registerColumnType(Types.FLOAT, "float");
		registerColumnType(Types.INTEGER, "int");
		registerColumnType(Types.VARCHAR, "varchar");

		registerColumnType(Types.OTHER, "uuid");
		registerColumnType(Types.JAVA_OBJECT, "list<text>");

		registerHibernateType(Types.OTHER, "uuid");
		registerHibernateType(Types.JAVA_OBJECT, "list<text>");
	}

	@Override
	public String getAddColumnString() {
		return "add column";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean supportsLimit() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getLimitString(final String sql, final boolean hasOffset) {
		return (
			sql + " LIMIT ?"
		);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean bindLimitParametersFirst() {
		return false;
	}

	@Override
	public boolean supportsSequences() {
		return false;
	}

	@Override
	public void contributeTypes(
		final org.hibernate.boot.model.TypeContributions typeContributions,
		final org.hibernate.service.ServiceRegistry serviceRegistry
	) {
		super.contributeTypes(typeContributions, serviceRegistry);
		typeContributions.contributeType(new NativeUUIDType());
		typeContributions.contributeType(new NativeListType());
	}


	public static class NativeUUIDType extends AbstractSingleColumnStandardBasicType<UUID> {

		public static final NativeUUIDType INSTANCE = new NativeUUIDType();
	
		public NativeUUIDType() {
			super(NativeUUIDSqlTypeDescriptor.INSTANCE, UUIDTypeDescriptor.INSTANCE);
		}

	
		public String getName() {
			return "uuid";
		}
	
		@Override
		protected boolean registerUnderJavaType() {
			return true;
		}

		public static class NativeUUIDSqlTypeDescriptor implements SqlTypeDescriptor {

			public static final NativeUUIDSqlTypeDescriptor INSTANCE = new NativeUUIDSqlTypeDescriptor();

			public int getSqlType() {
				return Types.OTHER;
			}

			@Override
			public boolean canBeRemapped() {
					return true;
			}

			public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
				return new BasicBinder<X>(javaTypeDescriptor, this) {
					@Override
					protected void doBind(
						final PreparedStatement st,
						final X value,
						final int index,
						final WrapperOptions options
					) throws SQLException {
						st.setObject(
							index,
							javaTypeDescriptor.unwrap(value, UUID.class, options),
							getSqlType()
						);
					}

					@Override
					protected void doBind(
						final CallableStatement st,
						final X value,
						final String name,
						final WrapperOptions options
					) throws SQLException {
						st.setObject(
							name,
							javaTypeDescriptor.unwrap(value, UUID.class, options),
							getSqlType()
						);
					}
				};
			}

			public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
				return new BasicExtractor<X>( javaTypeDescriptor, this ) {
					@Override
					protected X doExtract(final ResultSet rs, final String name, final WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(rs.getObject(name), options);
					}

					@Override
					protected X doExtract(final CallableStatement statement, final int index, final WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(statement.getObject(index), options);
					}

					@Override
					protected X doExtract(final CallableStatement statement, final String name, final WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(statement.getObject(name), options);
					}
				};
			}
		}
	}


	public static class NativeListType extends AbstractSingleColumnStandardBasicType<List> {

		public static final NativeListType INSTANCE = new NativeListType();
	
		public NativeListType() {
			super(NativeListSqlTypeDescriptor.INSTANCE, ListTypeDescriptor.INSTANCE);
		}

	
		public String getName() {
			return "list<text>";
		}
	
		@Override
		protected boolean registerUnderJavaType() {
			return true;
		}

		public static class NativeListSqlTypeDescriptor implements SqlTypeDescriptor {

			public static final NativeListSqlTypeDescriptor INSTANCE = new NativeListSqlTypeDescriptor();

			public int getSqlType() {
				return Types.JAVA_OBJECT;
			}

			@Override
			public boolean canBeRemapped() {
					return true;
			}

			public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
				return new BasicBinder<X>(javaTypeDescriptor, this) {
					@Override
					protected void doBind(
						final PreparedStatement st,
						final X value,
						final int index,
						final WrapperOptions options
					) throws SQLException {
						st.setObject(
							index,
							javaTypeDescriptor.unwrap(value, List.class, options),
							getSqlType()
						);
					}

					@Override
					protected void doBind(
						final CallableStatement st,
						final X value,
						final String name,
						final WrapperOptions options
					) throws SQLException {
						st.setObject(
							name,
							javaTypeDescriptor.unwrap(value, List.class, options),
							getSqlType()
						);
					}
				};
			}

			public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
				return new BasicExtractor<X>( javaTypeDescriptor, this ) {
					@Override
					protected X doExtract(final ResultSet resultSet, final String name, final WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(resultSet.getObject(name), options);
					}

					@Override
					protected X doExtract(final CallableStatement statement, final int index, final WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(statement.getObject(index), options);
					}

					@Override
					protected X doExtract(final CallableStatement statement, final String name, final WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(statement.getObject(name), options);
					}
				};
			}
		}
	}


	@Override
	public IdentifierHelper buildIdentifierHelper(
		final IdentifierHelperBuilder builder,
		final DatabaseMetaData dbMetaData
	) throws SQLException {

		builder.applyIdentifierCasing(dbMetaData);

		builder.applyReservedWords(dbMetaData);
		builder.applyReservedWords(AnsiSqlKeywords.INSTANCE.sql2003());
		builder.applyReservedWords(getKeywords());

		builder.setNameQualifierSupport(getNameQualifierSupport());

		builder.setQuotedCaseStrategy(IdentifierCaseStrategy.MIXED);
		builder.setUnquotedCaseStrategy(IdentifierCaseStrategy.LOWER);

		return builder.build();
	}

	@Override
	public String getQueryHintString(final String query, final List<String> hintList) {
		logger.fine(()-> "getQueryHintString(" + query + ", " + hintList + ")");
		return super.getQueryHintString(query, hintList);
	}

	@Override
	public String getQueryHintString(final String query, final String hints) {
		logger.fine(()-> "getQueryHintString(" + query + ", " + hints + ")");
		return super.getQueryHintString(query, hints);
	}

	/*
	@Override
	public String transformSelectString(final String select) {
		logger.info("transformSelectString(" + select + ")");
		return select;
	}
	*/

}


