
/*
 * vim: set nowrap:
 *
 */

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
import javax.persistence.Embedded;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;


@Embeddable
public class EventId implements Comparable<EventId>, java.io.Serializable {



        private SourceId sourceId = null;

	@AttributeOverrides({
		@AttributeOverride(name = "identity", column = @Column(name = "source_identity_value"))
	})
	@Embedded
	public SourceId getSourceId() {
		return sourceId;
	}

	public void setSourceId(final SourceId value) {
		sourceId = value;
	}


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


	private String type = null;

	@Column(
		name = "type",
		columnDefinition = "TEXT"
	)
	public String getType() {
		return type;
	}

	public void setType(final String value) {
		type = value;
	}


	public static final String defaultGroup = "default";

	private String group = null;

	@Column(
		name = "groupName",
		columnDefinition = "TEXT"
	)
	public String getGroup() {
		return group;
	}

	public void setGroup(final String value) {
		group = value;
	}


	private long reference = 0L;

	@Column(name = "reference")
	public long getReference() {
		return reference;
	}

	public void setReference(final long value) {
		reference = value;
	}


	private Date created = null;

	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(final Date value) {
		created = value;
	}


	public EventId() {
	}


	public EventId(
		final SourceId sourceId,
		final UUID identity,
		final String type,
		final String group,
		final long reference,
		final Date created
	) {
		this.sourceId = sourceId;
		this.identity = identity;
		this.type = type;
		this.group = group;
		this.reference = reference;
		this.created = created;
	}


	@Override
	public int hashCode() {
		return java.util.Objects.hash(
			sourceId,
			identity,
			type,
			group,
			reference,
			created
		);
	}


	@Override
	public boolean equals(final Object instance) {

		if (instance == null)
			return false;

		if (!isEqual(getSourceId(), ((EventId) instance).getSourceId()))
			return false;

		if (!isEqual(getIdentity(), ((EventId) instance).getIdentity()))
			return false;

		if (!isEqual(getType(), ((EventId) instance).getType()))
			return false;

		if (!isEqual(getGroup(), ((EventId) instance).getGroup()))
			return false;

		if (getReference() != ((EventId) instance).getReference())
			return false;

		if (!isEqual(getCreated(), ((EventId) instance).getCreated()))
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
	public int compareTo(final EventId instance) {

		{
			final int result = compare(getIdentity(), instance.getIdentity());
			if (result != 0)
				return result;
		}

		{
			final int result = compare(getType(), instance.getType());
			if (result != 0)
				return result;
		}

		{
			final int result = compare(getGroup(), instance.getGroup());
			if (result != 0)
				return result;
		}

		{
			final int result = Long.compare(getReference(), instance.getReference());
			if (result != 0)
				return result;
		}


		{
		//	final int result = compareTime(getCreated(), instance.getCreated());
			final int result = compare(getCreated(), instance.getCreated());

			if (result != 0)
				return (-result);
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

