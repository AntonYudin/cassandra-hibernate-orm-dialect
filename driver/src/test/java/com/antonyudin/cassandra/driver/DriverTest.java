
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


import java.util.logging.Logger;

import java.util.List;
import java.util.ArrayList;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DriverTest implements java.io.Serializable {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		Driver.class.getName()
	);


	@org.junit.jupiter.api.Test
	public void testLoad() throws java.lang.Exception {

		Class.forName("com.antonyudin.cassandra.driver.Driver");

		final java.util.Enumeration<java.sql.Driver> drivers = java.sql.DriverManager.getDrivers();

		int count = 0;

		while (drivers.hasMoreElements()) {
			final java.sql.Driver driver = drivers.nextElement();
			if (driver.getClass().getName().equals("com.antonyudin.cassandra.driver.Driver"))
				count++;
		}

		assertEquals(count, 1);
	}


	@org.junit.jupiter.api.Test
	public void testAccept() throws java.lang.Exception {

		Class.forName("com.antonyudin.cassandra.driver.Driver");

		final Driver driver = new Driver();

		assertTrue(driver.acceptsURL("jdbc:cassandra://127.0.0.1/keyspace"));
	}

	@org.junit.jupiter.api.Test
	@org.junit.jupiter.api.Tag("cassandra")
	public void testDatabaseVersion() throws java.lang.Exception {

		final java.sql.Connection connection = java.sql.DriverManager.getConnection(
			"jdbc:cassandra://127.0.0.1/jee",
			"jee",
			"jee"
		);

		final java.sql.DatabaseMetaData metaData = connection.getMetaData();

		logger.info("database major version: [" + metaData.getDatabaseMajorVersion() + "]");
		logger.info("database minor version: [" + metaData.getDatabaseMinorVersion() + "]");

		connection.close();
	}

}


