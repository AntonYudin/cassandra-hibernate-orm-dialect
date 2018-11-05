
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


import java.util.Map;

import java.util.logging.Logger;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.PreparedStatement;


public class PreparedStatementsCache implements java.io.Serializable {

	private final static Logger logger = Logger.getLogger(PreparedStatementsCache.class.getName());


	private final Driver.Context context;
	private final DefaultConnection connection;
	private final Session session;

	private final Map<String, PreparedStatement> cache = new java.util.concurrent.ConcurrentHashMap<>();


	PreparedStatementsCache(
		final Driver.Context context,
		final DefaultConnection connection,
		final Session session
	) {
		this.context = context;
		this.connection = connection;
		this.session = session;
	}


	public PreparedStatement get(final String sql) {

		PreparedStatement statement = cache.get(sql);

		if (statement == null) {

			statement = this.session.prepare(transformSQL(sql));

			cache.put(sql, statement);

			//logger.info("caching statement: [" + sql + "]");
		} else {
			//logger.info("using cached statement: [" + sql + "]");
		}

		return statement;
	}

	public void clear() {
		cache.clear();
	}

	protected String transformSQL(final String originalSQL) {
		return connection.getTransformer().transform(originalSQL);
	}

}

