
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

import com.arjuna.ats.arjuna.common.arjPropertyManager;


public abstract class AbstractTest {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		AbstractTest.class.getName()
	);

	protected EntityManager entityManager;
	protected EntityManagerFactory entityManagerFactory;

	protected abstract String getPersistenceUnitName();

	protected abstract boolean isRecreateTables();

//	private javax.transaction.UserTransaction transaction;
	private javax.transaction.Transaction transaction;
	private javax.transaction.TransactionManager transactionManager;

	@org.junit.jupiter.api.BeforeEach
	public void beforeEach() throws java.lang.Exception {
		logger.info("beforeEach()");

		/*
		logger.info("start transaction");

		transactionManager.begin();

		transaction = transactionManager.getTransaction();

		logger.info("transaction: ["  + transaction + "]");
		*/

	//	transaction.begin();

	//	entityManager.joinTransaction();
	}


	@org.junit.jupiter.api.AfterEach
	public void afterEach() throws java.lang.Exception {
		logger.info("afterEach()");
	//	logger.info("status: [" + transactionManager.getStatus() + "]");

	//	logger.info("commiting transaction");
	//	transaction.commit();
	//	transactionManager.rollback();
	}

	@org.junit.jupiter.api.BeforeAll
	public void beforeClass() throws java.lang.Exception {

		logger.info("before class");

		/*
		arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier("1");

		transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();

		logger.info("transactionManager: [" + transactionManager + "]");
		*/


		entityManagerFactory = Persistence.createEntityManagerFactory(getPersistenceUnitName());

		logger.info("entityManagerFactory: " + entityManagerFactory);

		entityManager = entityManagerFactory.createEntityManager();

		logger.info("entityManager: " + entityManager);

		/*
		if (isRecreateTables()) {

			final String createEventChildrenTable =
				"CREATE TABLE jee.event_children (" +
				"	child_id bigint," +
				"	identity uuid," +
				"	type text," +
				"	groupname text," +
				"	reference bigint," +
				"	created timestamp," +
				"	PRIMARY KEY (identity, type, groupname, reference, created, child_id)" +
				")"
			;

			final String createEventsTable =
				"create table events (" +
				"	created timestamp," +
				"	groupName TEXT," +
				"	identity uuid," +
				"	reference bigint," +
				"	type TEXT," +
				"	content TEXT," +
				"	data blob," +
				"	doubleValue double," +
				"	floatValue float," +
				"	listOfNames list<text>," +
				"	removed boolean," +
				"	shortValue smallint," +
				"	name TEXT," +
				"	primary key (identity, type, groupName, reference, created)" +
				")"
			;

			final String createChildrenTable =
				"CREATE TABLE jee.children (" +
				"	child_id bigint," +
				"	parent_id bigint," +
				"	PRIMARY KEY (parent_id, child_id)" +
				")"
			;

			final EntityTransaction transaction = entityManager.getTransaction();

			transaction.begin();

			entityManager.createNativeQuery("DROP TABLE events").executeUpdate();
			entityManager.createNativeQuery("DROP TABLE event_children").executeUpdate();
			entityManager.createNativeQuery("DROP TABLE children").executeUpdate();
			entityManager.createNativeQuery(createEventsTable).executeUpdate();
			entityManager.createNativeQuery(createEventChildrenTable).executeUpdate();
			entityManager.createNativeQuery(createChildrenTable).executeUpdate();

			transaction.commit();
		}
		*/

		logger.info("before class ... done");
	}

	
	@org.junit.jupiter.api.AfterAll
	public void afterClass() {

		entityManager.close();

		entityManagerFactory.close();

		logger.info("after class");
	}

}

