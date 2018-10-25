
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
import com.antonyudin.cassandra.model.ChildId;
import com.antonyudin.cassandra.model.Parent;
import com.antonyudin.cassandra.model.EventChild;
import com.antonyudin.cassandra.model.EventChild_;
import com.antonyudin.cassandra.model.ItemAdded;
import com.antonyudin.cassandra.model.Source;
import com.antonyudin.cassandra.model.SourceId;
import com.antonyudin.cassandra.model.SourceId_;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@javax.ejb.Startup
//@javax.ejb.Stateless
@javax.ejb.Singleton
@javax.ejb.Local
@javax.ejb.TransactionManagement(javax.ejb.TransactionManagementType.CONTAINER)
//@javax.ejb.TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class TestImplementation implements Test, java.io.Serializable {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		TestImplementation.class.getName()
	);

	@javax.persistence.PersistenceContext(unitName = "default")
	private javax.persistence.EntityManager entityManager;

//	@javax.persistence.PersistenceUnit(unitName = "default")
//	private javax.persistence.EntityManagerFactory entityManagerFactory;

//	@javax.annotation.Resource
//	private javax.transaction.UserTransaction transaction;

	@javax.annotation.PostConstruct
	@javax.annotation.security.PermitAll
	public void postConstruct() {
		logger.info("postConstruct()");
		test();
	}

	@javax.annotation.security.PermitAll
	@Override
	public void test() {

		logger.info("test()");

		try {

//			transaction.begin();

//			final javax.persistence.EntityManager entityManager = entityManagerFactory.createEntityManager();

			logger.info("entityManager: [" + entityManager + "]");


			final List<Source> sources = new ArrayList<>();

			for (int i = 0; i < 10; i++) {
				final Source source = new Source();
				source.setId(new SourceId(java.util.UUID.randomUUID()));
				source.setName("source - " + i);
				sources.add(source);
			}


			for (Source source: sources)
				entityManager.persist(source);


			entityManager.flush();
			entityManager.clear();

			final List<Event> events = new ArrayList<>();

			final java.util.Random random = new java.util.Random();

			{
				final java.util.UUID uuid = java.util.UUID.randomUUID();
				final Event event = new Event();
				final EventId id = new EventId();
				event.setId(id);

				id.setSourceId(sources.get(0).getId());
				id.setIdentity(uuid);
				id.setCreated(new Date());
				id.setGroup("group");

				event.setData(new byte[10]);
				event.setSource(sources.get(0));

				event.getListOfNames().add("test1");
				event.getListOfNames().add("test2");
				event.getListOfNames().add("test3");

				random.nextBytes(event.getData());

				events.add(event);
			}

			{
				final java.util.UUID uuid = java.util.UUID.randomUUID();
				final Event event = new Event();
				final EventId id = new EventId();
				event.setId(id);
				id.setSourceId(sources.get(0).getId());
				id.setIdentity(uuid);
				id.setCreated(new Date());
				id.setGroup("group");

				event.setData(new byte[10]);
				event.setSource(sources.get(0));

				random.nextBytes(event.getData());

				events.add(event);
			}

			{
				final java.util.UUID uuid = java.util.UUID.randomUUID();
				final ItemAdded event = new ItemAdded();
				final EventId id = new EventId();
				event.setId(id);
				id.setSourceId(sources.get(0).getId());
				id.setIdentity(uuid);
				id.setCreated(new Date());
				id.setGroup("group");

				event.setData(new byte[10]);
				event.setSource(sources.get(0));

				random.nextBytes(event.getData());

				events.add(event);
			}


			for (Event event: events)
				entityManager.persist(event);

			entityManager.flush();
			entityManager.clear();

			{
				final Event foundEvent = entityManager.find(Event.class, events.get(0).getId());

				logger.info("found event: [" + foundEvent + "]");
				logger.info("list of names: [" + foundEvent.getListOfNames() + "]");
			}

			{
				final ItemAdded foundEvent = (ItemAdded) entityManager.find(Event.class, events.get(2).getId());

				logger.info("found event: [" + foundEvent + "]");
				logger.info("list of names: [" + foundEvent.getListOfNames() + "]");
			}

			entityManager.clear();

			testSelectCriteria(events);

			entityManager.clear();

			{
				final Long id1 = random.nextLong();
				final Parent parent = new Parent();
				parent.setId(id1);
				entityManager.persist(parent);

				final Child child = new Child();
				final ChildId id = new ChildId();

				id.setParentId(parent.getId());
				id.setChildId(random.nextLong());

				child.setId(id);
			//	child.setParent(parent);

				entityManager.persist(child);

				entityManager.flush();
				entityManager.clear();
			}

			testSelectParents();


			final EventChild eventChild = new EventChild();
			final EventChildId eventChildId = new EventChildId(events.get(0).getId(), random.nextLong());
			eventChild.setId(eventChildId);
			entityManager.persist(eventChild);

			entityManager.flush();
			entityManager.clear();

			testSelectEventAndAccessChildren(events.get(0).getId());

			entityManager.flush();
			entityManager.clear();

			testSelectEventChildrenByEventId(events.get(0).getId());

			entityManager.flush();
			entityManager.clear();

			testNativeQuery(events.get(1).getId());


	//		transaction.commit();

	//		logger.info("persisting took [" + (System.currentTimeMillis() - time) + "]");

	//		entityManager.close();

		} catch (java.lang.Exception exception) {
			logger.warning("exception: " + exception);
			exception.printStackTrace(System.err);
		}

		logger.info("test() done.");
	}

	protected void testSelectCriteria(final List<Event> originalEvents) {

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		//criteria.distinct(true);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		logger.info("items.get(Event_.id): [" + items.get(Event_.id) + "]");
		logger.info("items.get(Event_.id).get(EventId_.identity): [" + items.get(Event_.id).get(EventId_.identity) + "]");

		final List<UUID> sourceIdentities = new ArrayList<>();

		for (Event event: originalEvents)
			sourceIdentities.add(event.getId().getSourceId().getIdentity());

		criteria.where(
			items.get(Event_.id).
			get(EventId_.sourceId).
			get(SourceId_.identity).
			in(sourceIdentities)
		);

		final List<Event> events = entityManager.createQuery(
			criteria
		).getResultList();

		logger.info("found events [" + events.size() + "] {");

		for (Event event: events) {
			logger.info("\tfound event: [" + event + "]");
		}

		logger.info("}");


		for (Event e: originalEvents) {
			logger.info("checking event: [" + e + "]");

			boolean found = false;

			for (Event event: events) {
				if (e.getId().equals(event.getId())) {
					logger.info("\tfound match for: [" + event + "]");
					logger.info("\t\tevent.class: [" + e.getClass() + "]");
					logger.info("\t\tevent.class: [" + event.getClass() + "]");
					found = true;
				}
			}

			if (!found)
				throw new IllegalArgumentException("cannot find match for event: [" + e + "]");
		}
	}


	protected void testSelectParents() {

		final CriteriaQuery<Parent> criteria = entityManager.getCriteriaBuilder().createQuery(
			Parent.class
		);

		//criteria.distinct(true);

		final Root<Parent> items = criteria.from(Parent.class);

		criteria.select(items);

		final List<Parent> parents = entityManager.createQuery(
			criteria
		).getResultList();

		for (Parent parent: parents) {
			logger.info("found parent: [" + parent + "]");

			logger.info("checking children ...");

			for (Child child: parent.getChildren()) {
				logger.info("\tchild: [" + child + "]");
			}

			logger.info("checking children ... done.");
		}
	}


	protected void testSelectEventChildrenByEventId(final EventId id) {

		final CriteriaQuery<EventChild> criteria = entityManager.getCriteriaBuilder().createQuery(
			EventChild.class
		);

		//criteria.distinct(true);

		final Root<EventChild> items = criteria.from(EventChild.class);

		criteria.select(items);

		criteria.where(
			entityManager.getCriteriaBuilder().equal(
				items.get(EventChild_.id).get(EventChildId_.eventId),
				id
			)
		);

		final List<EventChild> children = entityManager.createQuery(
			criteria
		).getResultList();

		logger.info("found [" + children.size() + "] children for event id: [" + id + "]");

		for (EventChild child: children) {
			logger.info("found child: [" + child + "]");
		}
	}





	protected void testSelectEventAndAccessChildren(final EventId id) {

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		//criteria.distinct(true);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		criteria.where(
			entityManager.getCriteriaBuilder().equal(
				items.get(Event_.id),
				id
			)
		);

		final List<Event> events = entityManager.createQuery(
			criteria
		).getResultList();

		for (Event parent: events) {

			logger.info("found parent event: [" + parent + "]");
			logger.info("list of names: [" + parent.getListOfNames() + "]");

			logger.info("checking children ...");

			for (EventChild child: parent.getChildren()) {
				logger.info("\tchild: [" + child + "]");
			}

			logger.info("checking children ... done.");
		}
	}


	protected void testNativeQuery(final EventId id) {

		final Query query = entityManager.createNativeQuery(
			"SELECT * FROM events WHERE source_identity = :identity"
		);
		
		for (javax.persistence.Parameter parameter: query.getParameters()) {
			logger.info("parameter: [" + parameter + "]");
			logger.info("\tname: [" + parameter.getName() + "]");
			logger.info("\ttype: [" + parameter.getParameterType() + "]");
			logger.info("\tposition: [" + parameter.getPosition() + "]");
		}

		query.setParameter("identity", (Object) id.getSourceId().getIdentity());

		final List<Object[]> result = query.getResultList();

		for (Object[] item: result) {
			logger.info("found: [" + item[0] + ", " + item[1] + ", " + item[2] + ", " + item[3] + "]");
		}
	}


}

