
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
import java.util.Map;
import java.util.HashMap;

import java.net.URI;


public class ConnectionString implements java.io.Serializable {

	private final static Logger logger = Logger.getLogger(ConnectionString.class.getName());


	private final static String parameters_tracing = "tracing";
	private final static String parameters_transformer = "transformer";
	private final static String parameters_allowFiltering = "allowFiltering";


	public ConnectionString(final String url) throws java.net.URISyntaxException {

		this.url = url;

		final String jdbcPrefix = "jdbc:";

		if (!url.startsWith(jdbcPrefix))
			throw new IllegalArgumentException("url does not start with " + jdbcPrefix);

		final String line = url.substring(jdbcPrefix.length());

		logger.info("line: [" + line + "]");

		final URI uri = new URI(line);

		/*
		logger.info("uri: [" + uri + "]");
		logger.info("uri.scheme: [" + uri.getScheme() + "]");
		logger.info("uri.host: [" + uri.getHost() + "]");
		logger.info("uri.port: [" + uri.getPort() + "]");
		logger.info("uri.path: [" + uri.getPath() + "]");
		*/

		if (!uri.getScheme().equalsIgnoreCase("cassandra"))
			throw new IllegalArgumentException("scheme is not cassandra, " + uri.getScheme());

		if ((uri.getHost() == null) || (uri.getHost().length() <= 0))
			throw new IllegalArgumentException("host cannot be empty");

		this.contactPoints.add(uri.getHost());

		this.keyspace = (uri.getPath().startsWith("/")? uri.getPath().substring(1): uri.getPath());

		final String queryString = uri.getRawQuery();

		final Map<String, String> parameters = parseParameters(queryString);

		logger.info("parameters: [" + parameters + "]");

		if (
			(parameters.get(parameters_tracing) != null) &&
			(parameters.get(parameters_tracing).equals("true"))
		)
			tracingEnabled = true;

		if (
			(parameters.get(parameters_allowFiltering) != null) &&
			(parameters.get(parameters_allowFiltering).equals("true"))
		)
			allowFiltering = true;

		transformer = parameters.get(parameters_transformer);
	}


	private final List<String> contactPoints = new ArrayList<>();

	public List<String> getContactPoints() {
		return contactPoints;
	}


	private final String keyspace;

	public String getKeyspace() {
		return keyspace;
	}


	private boolean tracingEnabled = false;

	public boolean isTracingEnabled() {
		return tracingEnabled;
	}


	private boolean allowFiltering = false;

	public boolean isAllowFiltering() {
		return allowFiltering;
	}



	private String transformer = null;

	public String getTransformer() {
		return transformer;
	}


	protected Map<String, String> parseParameters(final String query) {

		final Map<String, String> result = new HashMap<>();

		if (query == null)
			return result;

		for (int position = 0;;) {

			final int index = query.indexOf("&", position);

			final String pair = query.substring(
				position,
				index >= 0?
				index:
				query.length()
			);

			final int index1 = pair.indexOf("=");

			if (index1 >= 0) {
				result.put(
					pair.substring(0, index1),
					(pair.length() > (index1 + 1))?
					pair.substring(index1 + 1, pair.length()):
					""
				);
			}

			if (index < 0)
				break;

			position = index + 1;
		}

		return result;
	}


	private final String url;

	public String getURL() {
		return url;
	}

}

