
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

package com.antonyudin.cassandra.logic;


import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

import java.time.LocalDateTime;

import com.antonyudin.cassandra.model.Event;



@javax.ejb.Stateless
@javax.ejb.Local(Items.class)
@javax.ejb.TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class ItemsImplementation implements Items, java.io.Serializable {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		ItemsImplementation.class.getName()

	);

	@javax.annotation.security.PermitAll
	@Override
	public List<Event> fetch() throws java.lang.Exception {

		logger.info("fetch()");

		return null;
	}

}

