
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


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverride;


@Entity
@Table(
	name = "user_posts"
)
public class UserPost implements java.io.Serializable {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		UserPost.class.getName()
	);


	private UserPostId id;

	@EmbeddedId
	public UserPostId getId() {
		return id;
	}

	public void setId(final UserPostId value) {
		id = value;
	}


	private PostBasic post;

	@Embedded
	@AttributeOverride(
		name = "created",
		column = @Column(
			name = "post_created",
			insertable = false,
			updatable = false
		)
	)
	public PostBasic getPost() {
		return post;
	}

	public void setPost(final PostBasic value) {
		post = value;
	}

}

