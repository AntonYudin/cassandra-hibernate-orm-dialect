
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
import java.util.ArrayList;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;


public class ListTypeDescriptor extends AbstractTypeDescriptor<List> {

	public static final ListTypeDescriptor INSTANCE = new ListTypeDescriptor();


	public ListTypeDescriptor() {
		super(List.class);
	}

	public String toString(final List value) {
		return ToStringTransformer.INSTANCE.transform(value);
	}

	public List fromString(final String string) {
		return ToStringTransformer.INSTANCE.parse(string);
	}

	@SuppressWarnings({"unchecked"})
	public <X> X unwrap(final List value, final Class<X> type, final WrapperOptions options) {

		if (value == null)
			return null;

		if (List.class.isAssignableFrom(type))
			return (X) PassThroughTransformer.INSTANCE.transform(value);

		throw unknownUnwrap(type);
	}

	public <X> List wrap(final X value, final WrapperOptions options) {

		if (value == null)
			return null;

		if (List.class.isInstance(value))
			return PassThroughTransformer.INSTANCE.parse(value);

		throw unknownWrap(value.getClass());
	}


	public static interface ValueTransformer {
		public Serializable transform(final List list);
		public List parse(final Object value);
	}


	public static class PassThroughTransformer implements ValueTransformer {

		public static final PassThroughTransformer INSTANCE = new PassThroughTransformer();


		@Override
		public Serializable transform(final List value) {
			return (Serializable) value;
		}

		@Override
		public List parse(final Object value) {
			return (List) value;
		}
	}


	public static class ToStringTransformer implements ValueTransformer {

		public static final ToStringTransformer INSTANCE = new ToStringTransformer();


		public String transform(final List list) {
			return list.toString();
		}

		public List parse(final Object value) {
			throw new UnsupportedOperationException("not implemented");
		}
	}

}

