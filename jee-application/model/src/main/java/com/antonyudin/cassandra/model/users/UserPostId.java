
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

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;


@Embeddable
@MappedSuperclass
public class UserPostId implements java.io.Serializable {


	private UUID userIdentity = null;

	@Column(
		name = "user_identity"
	)
	public UUID getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(final UUID value) {
		userIdentity = value;
	}


	private LocalDateTime created = null;

	@Column(
		name = "post_created"
	)
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(final LocalDateTime value) {
		created = value;
	}


	private UUID postIdentity = null;

	@Column(
		name = "post_identity"
	)
	public UUID getPostIdentity() {
		return postIdentity;
	}

	public void setPostIdentity(final UUID value) {
		postIdentity = value;
	}


}

