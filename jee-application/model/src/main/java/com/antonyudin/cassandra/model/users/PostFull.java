
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
import javax.persistence.Entity;
import javax.persistence.Embedded;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.AttributeOverride;


@Embeddable
@MappedSuperclass
public class PostFull extends PostBasic {


	private String content = null;

	@Column(
		columnDefinition = "TEXT"
	)
	public String getContent() {
		return content;
	}

	public void setContent(final String value) {
		content = value;
	}


	@Column(name = "author_identity")
	public UUID getAuthorIdentity() {
		return (author != null? author.getIdentity(): null);
	}

	public void setAuthorIdentity(final UUID value) {
		author.setIdentity(value);
	}


	private UserBasic author;

	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "author_name", columnDefinition = "TEXT"))
	public UserBasic getAuthor() {
		return author;
	}

	public void setAuthor(final UserBasic value) {
		author = value;
	}

}

