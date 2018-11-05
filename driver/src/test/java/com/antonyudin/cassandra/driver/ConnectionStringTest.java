
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

public class ConnectionStringTest implements java.io.Serializable {


	@org.junit.jupiter.api.Test
	public void testParse() throws java.lang.Exception {

		final String prefix = "jdbc:";
		final String scheme = "cassandra";
		final String contactPoint = "host";
		final int port = 123;
		final String keyspace = "keyspace";

		final boolean tracingEnabled = true;


		final ConnectionString connectionString = new ConnectionString(
			prefix + scheme + "://" + contactPoint + ":" + port + "/" + keyspace + "?" +
			"tracing=" + tracingEnabled + "&" +
			"transformer=" + "hibernate"
		);

		assertTrue(connectionString.getContactPoints().contains(contactPoint));
	//	assertEquals(connectionString.getPort(), port);
		assertEquals(connectionString.getKeyspace(), keyspace);
		assertEquals(connectionString.isTracingEnabled(), tracingEnabled);
		assertEquals(connectionString.getTransformer(), "hibernate");
	}

}


