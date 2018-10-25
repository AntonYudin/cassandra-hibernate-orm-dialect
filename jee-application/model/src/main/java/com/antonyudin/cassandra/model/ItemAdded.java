
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorValue;


@Entity
@DiscriminatorValue(ItemAdded.discriminator)
public class ItemAdded extends Event {

	public final static String discriminator = "ItemAdded";

	public ItemAdded() {
	}


	@Override
	public void setId(final EventId value) {
		if ((value != null) && (value.getType() == null))
			value.setType(discriminator);
		super.setId(value);
	}

	private String name;

	@Column(columnDefinition = "TEXT")
	public String getName() {
		return name;
	}

	public void setName(final String value) {
		name = value;
	}

}

