
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
 
package com.antonyudin.cassandra.hibernate.dialect;


import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;


public class CassandraListTypeDescriptor extends AbstractTypeDescriptor<Object> {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		CassandraListTypeDescriptor.class.getName()
	);

	public static final CassandraListTypeDescriptor INSTANCE = new CassandraListTypeDescriptor();


	public CassandraListTypeDescriptor() {
		super(Object.class);
	}

	public String toString(final Object value) {

		if (value instanceof List)
			return ListToStringTransformer.INSTANCE.transform((List) value);

		return MapToStringTransformer.INSTANCE.transform((Map) value);
	}

	public Object fromString(final String string) {
		throw new IllegalArgumentException("not implemented");
	//	return ToStringTransformer.INSTANCE.parse(string);
	}

	@SuppressWarnings({"unchecked"})
	public <X> X unwrap(final Object value, final Class<X> type, final WrapperOptions options) {

		if (value == null)
			return null;

	//	logger.info("value: [" + value + "]");
	//	logger.info("type: [" + type + "]");

		if (List.class.isAssignableFrom(type))
			return (X) ListPassThroughTransformer.INSTANCE.transform((List) value);

		if (Map.class.isAssignableFrom(type))
			return (X) MapPassThroughTransformer.INSTANCE.transform((Map) value);

		throw unknownUnwrap(type);
	}

	public <X> Object wrap(final X value, final WrapperOptions options) {

		if (value == null)
			return null;

		if (List.class.isInstance(value))
			return ListPassThroughTransformer.INSTANCE.parse(value);

		if (Map.class.isInstance(value))
			return MapPassThroughTransformer.INSTANCE.parse(value);

		throw unknownWrap(value.getClass());
	}


	public static interface ListValueTransformer {
		public Serializable transform(final List list);
		public List parse(final Object value);
	}

	public static interface MapValueTransformer {
		public Serializable transform(final Map list);
		public Map parse(final Object value);
	}


	public static class ListPassThroughTransformer implements ListValueTransformer {

		public static final ListPassThroughTransformer INSTANCE = new ListPassThroughTransformer();


		@Override
		public Serializable transform(final List value) {
			return (Serializable) value;
		}

		@Override
		public List parse(final Object value) {
			return (List) value;
		}
	}


	public static class MapPassThroughTransformer implements MapValueTransformer {

		public static final MapPassThroughTransformer INSTANCE = new MapPassThroughTransformer();


		@Override
		public Serializable transform(final Map value) {
			return (Serializable) value;
		}

		@Override
		public Map parse(final Object value) {
			return (Map) value;
		}
	}



	public static class ListToStringTransformer implements ListValueTransformer {

		public static final ListToStringTransformer INSTANCE = new ListToStringTransformer();


		public String transform(final List list) {
			return list.toString();
		}

		public List parse(final Object value) {
			throw new UnsupportedOperationException("not implemented");
		}
	}


	public static class MapToStringTransformer implements MapValueTransformer {

		public static final MapToStringTransformer INSTANCE = new MapToStringTransformer();


		public String transform(final Map map) {
			return map.toString();
		}

		public Map parse(final Object value) {
			throw new UnsupportedOperationException("not implemented");
		}
	}

}

