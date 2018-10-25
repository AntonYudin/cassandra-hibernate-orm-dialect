
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


@Entity
@Table(
	name = "sources"
)
public class Source implements Comparable<Source>, java.io.Serializable {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		Source.class.getName()
	);


	private SourceId id;

	@EmbeddedId
	public SourceId getId() {
		return id;
	}

	public void setId(final SourceId value) {
		id = value;
	}


	private String name;

	@Column(columnDefinition = "TEXT")
	public String getName() {
		return name;
	}

	public void setName(final String value) {
		name = value;
	}


	@Override
	public int compareTo(final Source source) {

		final SourceId id0 = getId();
		final SourceId id1 = source.getId();

		if ((id0 == null) && (id1 == null))
			return 0;

		if (id0 == null)
			return -1;

		if (id1 == null)
			return 1;

		final int i = id0.compareTo(id1);

		if (i != 0)
			return i;

		if ((this.getName() == null) && (source.getName() == null))
			return 0;

		if (this.getName() == null)
			return -1;

		if (source.getName() == null)
			return 1;

		return this.getName().compareTo(source.getName());
	}

}

