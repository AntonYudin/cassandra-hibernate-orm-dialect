
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


import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.antonyudin.cassandra.model.Event;
import com.antonyudin.cassandra.model.Event_;
import com.antonyudin.cassandra.model.EventId_;
import com.antonyudin.cassandra.model.EventId;
import com.antonyudin.cassandra.model.EventChildId;
import com.antonyudin.cassandra.model.EventChildId_;
import com.antonyudin.cassandra.model.Child;
import com.antonyudin.cassandra.model.Parent;
import com.antonyudin.cassandra.model.EventChild;
import com.antonyudin.cassandra.model.EventChild_;
import com.antonyudin.cassandra.model.ItemAdded;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@org.junit.jupiter.api.TestInstance(org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS)
public class EntitiesCassandraTest extends AbstractEntitiesTest {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		EntitiesCassandraTest.class.getName()
	);


	@Override
	protected String getPersistenceUnitName() {
		return "test-cassandra";
	}

	@Override
	protected boolean isRecreateTables() {
		return true;
	}

}

