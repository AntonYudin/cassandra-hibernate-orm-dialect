
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

import java.sql.SQLException;


public class CommentsProcessor implements java.io.Serializable {

	private final static Logger logger = Logger.getLogger(CommentsProcessor.class.getName());

	private final List<Processor> processors = new ArrayList<>();



	CommentsProcessor(final String comments, final Options options) throws SQLException {

		processors.add((line) -> processTracing(line, options));
		processors.add((line) -> processAllowFiltering(line, options));

		processComments(comments, options);
	}


	protected void processTracing(final String line, final Options options) {
		if (line.equals("enableTracing"))
			options.enableTracing();
	}

	protected void processAllowFiltering(final String line, final Options options) {
		if (line.equals("allowFiltering"))
			options.allowFiltering();
	}

	protected void processComments(final String comments, final Options options) {

		if (comments == null)
			return;

		final String content = (
			(comments.startsWith("/*") && comments.endsWith("*/"))?
			comments.substring(2, comments.length() - 2).trim():
			comments.trim()
		);

		for (String line: content.split(";")) {
			for (Processor processor: processors)
				processor.process(line);
		}
	}

	public interface Options {
		public void enableTracing();
		public void allowFiltering();
	}


	public interface Processor {
		public void process(final String line);
	}

}

