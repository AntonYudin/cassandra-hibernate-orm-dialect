
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

package com.antonyudin.cassandra.model;


import java.util.List;


@javax.persistence.Converter(autoApply = false)
public class ListOfStringsAsObjectConverter implements javax.persistence.AttributeConverter<List<String>, Object> {

	private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		ListOfStringsAsObjectConverter.class.getName()
	);


	@Override
	public Object convertToDatabaseColumn(final List<String> value) {
		return value;
	}


	@Override
	public List<String> convertToEntityAttribute(final Object value) {
		return (List<String>) value;
	}

}

