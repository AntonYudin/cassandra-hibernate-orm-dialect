
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


import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Embeddable;
import javax.persistence.OrderBy;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Index;
import javax.persistence.Convert;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.FetchType;


//@MappedSuperclass
//@Embeddable

@Entity
@Table(
	name = "events",
	indexes = {
		@Index(name = "events_index_content",  columnList = "content"),
		@Index(name = "events_index_content2",  columnList = "content2")
	}
)
@Inheritance(strategy = javax.persistence.InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(Event.discriminator)

@org.hibernate.annotations.DiscriminatorFormula("type")

// XXX does not work, requires a fieldbridge
// @org.hibernate.search.annotations.Indexed
public class Event implements Comparable<Event>, java.io.Serializable {

	public static final String discriminator = "Event";

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		Event.class.getName()
	);


	private EventId idField;

	@EmbeddedId
	public EventId getId() {
		return idField;
	}

	public void setId(final EventId value) {
		if ((value != null) && (value.getType() == null))
			value.setType(discriminator);
		idField = value;
	}


	private Source source;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("sourceId")
	public Source getSource() {
		return source;
	}

	public void setSource(final Source value) {
		source = value;
	}



	private Date created;

	@Transient
	public Date getCreated() {

		if ((getId() != null) && (getId().getCreated() != null))
			return getId().getCreated();

		return created;
	}

	public void setCreated(final Date value) {

		if (getId() != null)
			throw new IllegalArgumentException("cannot set created for existing id");

		created = value;
	}


	private long reference;

	@Transient
	public long getReference() {

		if (getId() != null)
			return getId().getReference();

		return reference;
	}

	public void setReference(final long value) {

		if (getId() != null)
			throw new IllegalArgumentException("cannot set reference for existing id");

		reference = value;
	}


	/*
	@Column(name = "typed", insertable = false, updatable = false)
	private String typed;
	*/


	private String group = EventId.defaultGroup;

	@Transient
	public String getGroup() {

		if (getId() != null)
			getId().getGroup();

		return group;
	}

	public void setGroup(final String value) {

		if (getId() != null)
			throw new IllegalArgumentException("cannot change group");

		group = value;
	}


	private LocalDateTime createdLocalDateTime;

	@Column
	public LocalDateTime getCreatedLocalDateTime() {
		return createdLocalDateTime;
	}

	public void setCreatedLocalDateTime(final LocalDateTime value) {
		createdLocalDateTime = value;
	}


	private String content;

	@Column(
		columnDefinition = "TEXT"
	)
	public String getContent() {
		return content;
	}

	public void setContent(final String value) {
		content = value;
	}


	private String content2;

	@Column(
		columnDefinition = "TEXT"
	)
	public String getContent2() {
		return content2;
	}

	public void setContent2(final String value) {
		content2 = value;
	}


	private boolean removed = false;

	@Column
	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(final boolean value) {
		removed = value;
	}



	private byte[] data = null;

	@Lob
	@Column
	public byte[] getData() {
		return data;
	}

	public void setData(final byte[] value) {
		data = value;
	}


	private List<String> listOfNames = new ArrayList<>();

	//@Basic
	//(columnDefinition = "list<text>")
	//@Convert(converter = ListOfStringsAsObjectConverter.class)

//	@Transient
	public List<String> getListOfNames() {
		return listOfNames;
	}

	public void setListOfNames(final List<String> value) {
		listOfNames = value;
	}


	private float floatValue = 1.0f;

	@Column
	public float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(final float value) {
		floatValue = value;
	}


	private double doubleValue = 1.0;

	@Column
	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(final double value) {
		doubleValue = value;
	}


	private short shortValue = 1;

	@Column
	public short getShortValue() {
		return shortValue;
	}

	public void setShortValue(final short value) {
		shortValue = value;
	}





	private List<EventChild> children = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
//	@Transient
	public List<EventChild> getChildren() {
		return children;
	}

	public void setChildren(final List<EventChild> value) {
		children = value;
	}



//	@javax.persistence.PostLoad
	public void postLoad() {
		logger.info("postLoad()");
	}


	@Override
	public int compareTo(final Event event) {

		final EventId id0 = getId();
		final EventId id1 = event.getId();

		if ((id0 == null) && (id1 == null))
			return 0;

		if (id0 == null)
			return -1;

		if (id1 == null)
			return 1;

		final int i = id0.compareTo(id1);

		if (i != 0)
			return i;

		return Long.compare(this.hashCode(), event.hashCode());
	}

}

