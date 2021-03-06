
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

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;


@Entity
@Table(
	name = "users"
)
public class User extends UserFull {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		User.class.getName()
	);


	@Override
	@Id
	public UUID getIdentity() {
		return super.getIdentity();
	}


	private List<UserPost> posts = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	public List<UserPost> getPosts() {
		return posts;
	}

	public void setPosts(final List<UserPost> value) {
		posts = value;
	}

}

