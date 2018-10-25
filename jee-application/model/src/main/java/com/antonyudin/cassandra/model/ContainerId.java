
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


@Embeddable
public class ContainerId implements java.io.Serializable {


	private UUID identity = null;

	@Column(
	/*
		columnDefinition = "uuid"
	*/
	)
//	@Convert(converter = UUIDAsObjectConverter.class)
//	@org.hibernate.annotations.Type(type = "uuid-char")
	public UUID getIdentity() {
		return identity;
	}

	public void setIdentity(final UUID value) {
		identity = value;
	}

}

