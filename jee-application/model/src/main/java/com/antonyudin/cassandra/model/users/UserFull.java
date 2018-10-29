
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


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;


@Embeddable
@MappedSuperclass
public class UserFull extends UserBasic {


	private LocalDate dateOfBirth = null;

	@Column
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final LocalDate value) {
		dateOfBirth = value;
	}

}

