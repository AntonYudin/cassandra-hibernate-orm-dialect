
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


public class TestSQLTransformer implements SQLTransformer {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		TestSQLTransformer.class.getName()
	);


	public TestSQLTransformer() {
		logger.info("TestSQLTransformer created");
	}

	@Override
	public String transform(final String sql) {
		return sql;
	}

}


