
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


import java.util.UUID;


@javax.persistence.Converter(autoApply = false)
public class UUIDAsObjectConverter implements javax.persistence.AttributeConverter<UUID, Object> {

	private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		UUIDAsObjectConverter.class.getName()
	);


	@Override
	public Object convertToDatabaseColumn(final UUID value) {
		return value;
	}


	@Override
	public UUID convertToEntityAttribute(final Object value) {
		return (UUID) value;
	}

}

