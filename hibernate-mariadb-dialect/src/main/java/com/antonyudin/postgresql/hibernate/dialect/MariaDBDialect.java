
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

package com.antonyudin.postgresql.hibernate.dialect;


import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Locale;
import java.util.UUID;
import java.util.List;

import org.hibernate.JDBCException;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.function.AvgWithArgumentCastFunction;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.dialect.identity.HSQLIdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.lock.LockingStrategy;
import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
import org.hibernate.dialect.lock.OptimisticLockingStrategy;
import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
import org.hibernate.dialect.lock.PessimisticReadSelectLockingStrategy;
import org.hibernate.dialect.lock.PessimisticWriteSelectLockingStrategy;
import org.hibernate.dialect.lock.SelectLockingStrategy;
import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.LimitHelper;
import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.hibernate.hql.spi.id.IdTableSupportStandardImpl;
import org.hibernate.hql.spi.id.MultiTableBulkIdStrategy;
import org.hibernate.hql.spi.id.global.GlobalTemporaryTableBulkIdStrategy;
import org.hibernate.hql.spi.id.local.AfterUseAction;
import org.hibernate.hql.spi.id.local.LocalTemporaryTableBulkIdStrategy;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.JdbcExceptionHelper;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.persister.entity.Lockable;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.UUIDCharType;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;

import org.jboss.logging.Logger;

import java.sql.DatabaseMetaData;
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;


import org.hibernate.dialect.Dialect;


public class MariaDBDialect extends org.hibernate.dialect.MariaDB103Dialect {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		MariaDBDialect.class.getName()
	);

	public MariaDBDialect() {

		super();

		logger.info("MariaDBDialect() ...");

//		registerColumnType(Types.OTHER, "uuid");
//		registerColumnType(Types.BINARY, 16, "binary($1)");
//		registerHibernateType(Types.OTHER, "uuid");
	}


	@Override
	public void contributeTypes(
		final org.hibernate.boot.model.TypeContributions typeContributions,
		final org.hibernate.service.ServiceRegistry serviceRegistry
	) {
		super.contributeTypes(typeContributions, serviceRegistry);
		typeContributions.contributeType(new NativeUUIDType());
	//	typeContributions.contributeType(new UUIDCharType());
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
	//			return Types.OTHER;
				return Types.BINARY;
			}

			@Override
			public boolean canBeRemapped() {
					return true;
			}


			public UUID toUUID(final byte[] value) {

				if (value == null)
					return null;

				final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(value);

				return new UUID(buffer.getLong(), buffer.getLong());
			}

			public byte[] toBytes(final UUID value) {

				if (value == null)
					return null;

				final java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(new byte[16]);

				final long ms = value.getMostSignificantBits();
				buffer.putLong(ms);
				final long ls = value.getLeastSignificantBits();
				buffer.putLong(ls);

			//	logger.info("toBytes(): [" + ms + ":" + ls + "]");

				return buffer.array();
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
						try {
			//				logger.info("bind(" + value + ")");
							st.setObject(
								index,
								toBytes(javaTypeDescriptor.unwrap(value, UUID.class, options)),
								getSqlType()
							);
						} catch (java.lang.Exception exception) {
							logger.log(java.util.logging.Level.WARNING, exception.toString(), exception);
							throw exception;
						}
					}

					@Override
					protected void doBind(
						final CallableStatement st,
						final X value,
						final String name,
						final WrapperOptions options
					) throws SQLException {
						try {
			//				logger.info("bind(" + value + ")");
							st.setObject(
								name,
								toBytes(javaTypeDescriptor.unwrap(value, UUID.class, options)),
								getSqlType()
							);
						} catch (java.lang.Exception exception) {
							logger.log(java.util.logging.Level.WARNING, exception.toString(), exception);
							throw exception;
						}
					}
				};
			}

			public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
				return new BasicExtractor<X>( javaTypeDescriptor, this ) {
					@Override
					protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(rs.getObject(name), options);
					}

					@Override
					protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(statement.getObject(index), options);
					}

					@Override
					protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
						return javaTypeDescriptor.wrap(statement.getObject(name), options);
					}
				};
			}
		}
	}

} 
