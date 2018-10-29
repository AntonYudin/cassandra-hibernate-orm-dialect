
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

package com.antonyudin.cassandra.model.users;


import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


@Embeddable
@MappedSuperclass
public class UserBasic implements java.io.Serializable {


	private UUID identity = null;

//	XXX Hibernate does not allow to override a @MappedSuperclass @Column field and make it an @Id
//	it does allow to override a @Transient field and make it into an @Id
//	HHH-11771
//
//	@Column(
//		name = "identity"
//	)
	@Transient
	public UUID getIdentity() {
		return identity;
	}

	public void setIdentity(final UUID value) {
		identity = value;
	}


	private String name = null;

	@Column(
		columnDefinition = "TEXT"
	)
	public String getName() {
		return name;
	}

	public void setName(final String value) {
		name = value;
	}

}

