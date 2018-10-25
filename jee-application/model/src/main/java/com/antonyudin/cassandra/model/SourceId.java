
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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


@Embeddable
public class SourceId implements Comparable<SourceId>, java.io.Serializable {


	private UUID identity = null;

	@Column(
		name = "identity"/*,
		columnDefinition = "uuid"*/
	)
//	@org.hibernate.annotations.Type(type = "uuid-char")
	public UUID getIdentity() {
		return identity;
	}

	public void setIdentity(final UUID value) {
		identity = value;
	}


	public SourceId() {
	}


	public SourceId(
		final UUID identity
	) {
		this.identity = identity;
	}


	@Override
	public int hashCode() {
		return java.util.Objects.hash(
			identity
		);
	}


	@Override
	public boolean equals(final Object instance) {

		if (instance == null)
			return false;

		if (!isEqual(getIdentity(), ((SourceId) instance).getIdentity()))
			return false;

		return true;
	}


	protected boolean isEqual(final Object item0, final Object item1) {

		if ((item0 == null) && (item1 == null))
			return true;

		if ((item0 == null) || (item1 == null))
			return false;

		return item0.equals(item1);
	}



	@Override
	public int compareTo(final SourceId instance) {

		{
			final int result = compare(getIdentity(), instance.getIdentity());

			if (result != 0)
				return result;
		}

		return 0;
	}


	protected int compareTime(final UUID item0, final UUID item1) {

		if ((item0 == null) && (item1 == null))
			return 0;

		if (item0 == null)
			return -1;

		if (item1 == null)
			return 1;

		return Long.compare(item0.timestamp(), item1.timestamp());
	}


	protected <T extends Comparable<T>> int compare(final T item0, final T item1) {

		if ((item0 == null) && (item1 == null))
			return 0;

		if (item0 == null)
			return -1;

		if (item1 == null)
			return 1;

		return item0.compareTo(item1);
	}

}

