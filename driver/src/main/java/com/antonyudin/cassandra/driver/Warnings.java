
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


import java.util.List;
import java.util.ArrayList;

import java.sql.SQLWarning;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ExecutionInfo;


public class Warnings implements java.io.Serializable {

	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		Warnings.class.getName()
	);


	private final List<String> items = new ArrayList<>();


	public SQLWarning getSQLWarnings() {

		if (items.isEmpty())
			return null;

		final SQLWarning result = new SQLWarning(items.get(0));
		SQLWarning current = result;

		for (int i = 1; i < items.size(); i++) {
			final SQLWarning next = new SQLWarning(items.get(i));
			current.setNextWarning(next);
			current = next;
		}

		return result;
	}

	public void clear() {
		items.clear();
	}

	public void add(final ResultSet resultSet) {
		for (ExecutionInfo executionInfo: resultSet.getAllExecutionInfo()) {
			final List<String> warnings = executionInfo.getWarnings();
			if (warnings != null)
				items.addAll(warnings);
		}
	}

}

