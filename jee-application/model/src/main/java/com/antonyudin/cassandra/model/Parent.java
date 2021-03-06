
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
import java.util.ArrayList;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Embeddable;
import javax.persistence.OrderBy;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.JoinColumn;
import javax.persistence.IdClass;
import javax.persistence.FetchType;


@Entity
@Table(name = "parents")
public class Parent implements java.io.Serializable {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		Parent.class.getName()
	);


	private Long id;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(final Long value) {
		id = value;
	}



	private List<Child> children = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER)
	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(final List<Child> value) {
		logger.info("setChildren: [" + value + "]");
		children = value;
	}

}

