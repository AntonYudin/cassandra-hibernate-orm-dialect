
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

package com.antonyudin.cassandra.model;


import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import java.time.LocalDateTime;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.antonyudin.cassandra.model.Source;
import com.antonyudin.cassandra.model.Source_;
import com.antonyudin.cassandra.model.SourceId;
import com.antonyudin.cassandra.model.SourceId_;
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

import com.antonyudin.cassandra.model.users.User;
import com.antonyudin.cassandra.model.users.UserBasic;
import com.antonyudin.cassandra.model.users.UserFull;
import com.antonyudin.cassandra.model.users.Post;
import com.antonyudin.cassandra.model.users.Post_;
import com.antonyudin.cassandra.model.users.PostBasic;
import com.antonyudin.cassandra.model.users.PostFull;
import com.antonyudin.cassandra.model.users.UserPost;
import com.antonyudin.cassandra.model.users.UserPostId;


public abstract class AbstractEntitiesTest extends AbstractTest {

	private final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
		AbstractEntitiesTest.class.getName()
	);



	private final List<Source> sources = new ArrayList<>();


	protected List<Source> getSources() {

		if (sources.isEmpty()) {

			for (int i = 0; i < 5; i++) {
				final java.util.UUID uuid = java.util.UUID.randomUUID();
				final Source source = new Source();
				final SourceId id = new SourceId();
				id.setIdentity(uuid);
				source.setId(id);
				source.setName("source - " + i);
				sources.add(source);
			}


			final EntityTransaction transaction = entityManager.getTransaction();

			transaction.begin();

			for (Source source: sources) {

				logger.info("persisting [" + source + "]");
				logger.info("\tid: [" + source.getId().getIdentity() + "]");

				entityManager.persist(source);

				assertTrue(entityManager.contains(source));
			}

			logger.info("flush ...");
			entityManager.flush();
			transaction.commit();
			entityManager.clear();
			logger.info("flush ... done.");
		}

		return sources;
	}


	@org.junit.jupiter.api.Test
	public void testPersist() {

		logger.info("testPersist()");

		final List<Event> events = generateEvents(4);

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();
	}


	@org.junit.jupiter.api.Test
	public void testFindById() {

		final List<Event> events = generateEvents(4);

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		for (Event event: events) {
			final Event e = entityManager.find(Event.class, event.getId());
			assertTrue(e != null);
			assertTrue(e.getClass().equals(event.getClass()));
			logger.info("classes: [" + e.getClass() + ", " + event.getClass() + "]");
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();
	}


	@org.junit.jupiter.api.Test
	public void testSelectCriteriaById() {

		final List<Event> events = generateEvents(4);

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));

			for (EventChild child: event.getChildren()) {

				entityManager.persist(child);

				assertTrue(entityManager.contains(child));
			}
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		criteria.where(
			entityManager.getCriteriaBuilder().equal(
				items.get(Event_.id),
				events.get(0).getId()
			)
		);


		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).getResultList();

		assertEquals(foundEvents.size(), 1);

		for (Event event: foundEvents) {
		//	logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
			//	logger.info("\tchild: [" + child + "]");

			}

			assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}


	@org.junit.jupiter.api.Test
	public void testSelecJPQLByInId() {

		final List<Event> events = generateEvents(4);

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();

		transaction.begin();

		final List<UUID> identities = List.of(
			events.get(0).getId().getIdentity(),
			events.get(2).getId().getIdentity(),
			events.get(4).getId().getIdentity()
		);

		final List<UUID> sourceIdentities = List.of(
			events.get(0).getId().getSourceId().getIdentity(),
			events.get(2).getId().getSourceId().getIdentity(),
			events.get(4).getId().getSourceId().getIdentity()
		);

/*
		final List<Event> foundEvents = entityManager.createQuery(
			"SELECT e FROM Event e WHERE e.id.identity in (:identities)"
		).setParameter("identities", identities).getResultList();
*/

		final List<Event> foundEvents = entityManager.createQuery(
			"SELECT e FROM Event e WHERE e.id.sourceId.identity in (:sourceIdentities)"
		).setParameter("sourceIdentities", sourceIdentities).getResultList();

		logger.info("found events: [" + foundEvents.size() + "]");

	//	assertEquals(foundEvents.size(), 6);

		for (Event event: foundEvents) {

	//		logger.info("\tfound event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
	//			logger.info("\t\tchild: [" + child + "]");

			}

			if (event instanceof ItemAdded)
				assertEquals(event.getChildren().size(), 0);
			else
				assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}


	@org.junit.jupiter.api.Test
	public void testSelectCriteriaByInId() {

		final List<Event> events = generateEvents(4);

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final List<UUID> identities = List.of(
			events.get(0).getId().getIdentity(),
			events.get(2).getId().getIdentity()
		);

		final List<UUID> sourceIdentities = List.of(
			events.get(0).getId().getSourceId().getIdentity(),
			events.get(2).getId().getSourceId().getIdentity()
		);


		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		logger.info("0: " + Event_.id);
		logger.info("1: " + items.get(Event_.id));
		logger.info("2: " + EventId_.identity);
		logger.info("3: " + items.get(Event_.id).get(EventId_.identity));

	//	criteria.where(items.get(Event_.id).get(EventId_.identity).in(identities));
		criteria.where(
			items.get(Event_.id)
			.get(EventId_.sourceId)
			.get(SourceId_.identity)
			.in(sourceIdentities)
		);

//		criteria.where(items.get("id").get("identity").in(identities));

		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).getResultList();

//		assertEquals(foundEvents.size(), 4);

		for (Event event: foundEvents) {
	//		logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
	//			logger.info("\tchild: [" + child + "]");

			}

			if (event instanceof ItemAdded)
				assertEquals(event.getChildren().size(), 0);
			else
				assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}

	@org.junit.jupiter.api.Test
	public void testNativeQuery() {

		final List<Event> events = generateEvents(1);

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final Query query = entityManager.createNativeQuery(
//			"SELECT * FROM events WHERE identity = :identity"
			"SELECT * FROM events WHERE source_identity = :sourceIdentity"
		);
		
		for (javax.persistence.Parameter parameter: query.getParameters()) {
			logger.info("parameter: [" + parameter + "]");
			logger.info("\tname: [" + parameter.getName() + "]");
			logger.info("\ttype: [" + parameter.getParameterType() + "]");
			logger.info("\tposition: [" + parameter.getPosition() + "]");
		}

//		query.setParameter("identity", events.get(0).getId().getIdentity());
		query.setParameter("sourceIdentity", events.get(0).getId().getSourceId().getIdentity());

		final List<Object[]> result = query.getResultList();

		for (Object[] item: result) {
		//	logger.info("found: [" + item[0] + ", " + item[1] + ", " + item[2] + ", " + item[3] + "]");
		}

		transaction.commit();
	}


	@org.junit.jupiter.api.Test
	public void testSelectCriteriaWithMaxResults() {


		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).setMaxResults(2).getResultList();

		assertEquals(foundEvents.size(), 2);

		for (Event event: foundEvents) {
			logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
				logger.info("\tchild: [" + child + "]");

			}

			if (event instanceof ItemAdded)
				assertEquals(0, event.getChildren().size());
			else
				assertEquals(3, event.getChildren().size());
		}

		transaction.commit();
	}



	protected final java.util.Random random = new java.util.Random();

	protected <T extends Event> T createEvent(
		final UUID identity,
		final T instance,
		final List<Source> sources
	) {

		if (sources.isEmpty())
			throw new IllegalArgumentException("sources is empty");


		final java.util.UUID uuid = java.util.UUID.randomUUID();
		final EventId id = new EventId();
		instance.setId(id);

		id.setIdentity(identity);
		id.setCreated(new Date());
		id.setGroup("group");

		instance.setSource(entityManager.find(Source.class, sources.get(0).getId()));

//		logger.info("instance.id: [" + instance.getId() + "]");
//		logger.info("instance.source: [" + instance.getSource() + "]");
//		logger.info("instance.source.id: [" + instance.getSource().getId() + "]");

		instance.getId().setSourceId(instance.getSource().getId());
		instance.setCreatedLocalDateTime(LocalDateTime.now());

		return instance;
	}


	protected List<Event> generateEvents(final int numberOfEntities) {

		final List<Source> sources = getSources();

		final List<Event> result = new ArrayList<>();

		for (int i = 0; i < numberOfEntities; i++) {

			final java.util.UUID uuid = java.util.UUID.randomUUID();
			final Event event = createEvent(uuid, new Event(), sources);

			event.setData(new byte[10]);

			random.nextBytes(event.getData());

			event.setContent(generateString(128));
			event.setContent2(generateString(128));

			event.setShortValue((short) random.nextInt());
			event.setDoubleValue(random.nextDouble());
			event.setFloatValue((float) random.nextDouble());

			event.getListOfNames().add("test1 - " + random.nextLong());
			event.getListOfNames().add("test2 - " + random.nextLong());
			event.getListOfNames().add("test3 - " + random.nextLong());

			for (int j = 0; j < 3; j++) {
				final EventChild child = new EventChild();
				final EventChildId childId = new EventChildId();

				childId.setEventId(event.getId());
				childId.setChildId(random.nextLong());

				child.setId(childId);
				child.setEvent(event);

				event.getChildren().add(child);
			}

			result.add(event);

			final ItemAdded itemAdded = createEvent(uuid, new ItemAdded(), sources);

			result.add(itemAdded);
		}

		logger.info("generated [" + result.size() + "] events.");

		return result;
	}


	@org.junit.jupiter.api.Test
	public void testPersistSpeed() {

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		final List<Event> events = generateEvents(1000);

		final long started = System.nanoTime();

		for (Event event: events) {

			entityManager.persist(event);

	//		assertTrue(entityManager.contains(event));
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();

		logger.info("persisted [" + events.size() + "] events.");

		logger.info("PERSIST SPEED [" + getPersistenceUnitName() + "]: [" + ((System.nanoTime() - started) / (1000 * 1000)) + "].");
	}


	@org.junit.jupiter.api.Test
	public void testSelectCriteriaByIndexedColumnContent() {


		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		final List<Event> events = generateEvents(4);

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));

			for (EventChild child: event.getChildren()) {

				entityManager.persist(child);

				assertTrue(entityManager.contains(child));
			}
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		criteria.where(
			entityManager.getCriteriaBuilder().equal(
				items.get(Event_.content),
				events.get(0).getContent()
			)
		);

		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).getResultList();

		assertEquals(foundEvents.size(), 1);

		for (Event event: foundEvents) {
			logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
				logger.info("\tchild: [" + child + "]");

			}

			assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}


	@org.junit.jupiter.api.Test
	public void testSelectCriteriaByIndexedColumnContent2CustomIndex() {


		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		final List<Event> events = generateEvents(4);

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));

			for (EventChild child: event.getChildren()) {

				entityManager.persist(child);

				assertTrue(entityManager.contains(child));
			}
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		final String originalContent = (
			events.get(2).getContent2().substring(
				1, events.get(2).getContent2().length() - 1
			)
		);

		criteria.where(
			entityManager.getCriteriaBuilder().like(
				items.get(Event_.content2),
				"%" + originalContent + "%"
			)
		);

		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).getResultList();

		assertEquals(foundEvents.size(), 1);

		assertEquals(foundEvents.get(0).getContent2(), events.get(2).getContent2());

		for (Event event: foundEvents) {
			logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
				logger.info("\tchild: [" + child + "]");

			}

			assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}

	@org.junit.jupiter.api.Test
	public void testSelectCriteriaByPartitionKeyAndIndexedColumnContent2CustomIndex() {


		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		final List<Event> events = generateEvents(4);

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));

			for (EventChild child: event.getChildren()) {

				entityManager.persist(child);

				assertTrue(entityManager.contains(child));
			}
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		final String originalContent = (
			events.get(0).getContent2().substring(
				1, events.get(0).getContent2().length() - 1
			)
		);

		criteria.where(
			entityManager.getCriteriaBuilder().and(
				entityManager.getCriteriaBuilder().equal(
					items.get(Event_.id).get(EventId_.sourceId).get(SourceId_.identity),
					events.get(0).getId().getSourceId().getIdentity()
				),
				entityManager.getCriteriaBuilder().like(
					items.get(Event_.content2),
					"%" + originalContent + "%"
				)
			)
		);

		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).getResultList();

		assertEquals(foundEvents.size(), 1);

		assertEquals(foundEvents.get(0).getContent2(), events.get(0).getContent2());

		for (Event event: foundEvents) {
			logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
				logger.info("\tchild: [" + child + "]");

			}

			assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}


	@org.junit.jupiter.api.Test
	@org.junit.jupiter.api.Disabled
	public void testSelectCriteriaByIdAndDoubleValue() {

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		final List<Event> events = generateEvents(4);

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));

			for (EventChild child: event.getChildren()) {

				entityManager.persist(child);

				assertTrue(entityManager.contains(child));
			}
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		criteria.where(
			entityManager.getCriteriaBuilder().and(
				entityManager.getCriteriaBuilder().equal(
					items.get(Event_.id),
					events.get(1).getId()
				),
				entityManager.getCriteriaBuilder().equal(
					items.get(Event_.doubleValue),
					events.get(1).getDoubleValue()
				)
			)
		);

		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).getResultList();

		assertEquals(foundEvents.size(), 1);

		assertEquals(foundEvents.get(0).getContent2(), events.get(1).getContent2());

		for (Event event: foundEvents) {
			logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
				logger.info("\tchild: [" + child + "]");

			}

			assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}



	private final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXY0Z123456789";

	protected String generateString(final int length) {

		final StringBuilder result = new StringBuilder(length);

		for (int i = 0; i < length; i++)
			result.append(characters.charAt((int) (random.nextDouble() * characters.length())));

		return result.toString();
	}


	@org.junit.jupiter.api.Test
	public void testSelectCriteriaByIdWithHint() {

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		final List<Event> events = generateEvents(4);

		for (Event event: events) {

			entityManager.persist(event);

			assertTrue(entityManager.contains(event));

			for (EventChild child: event.getChildren()) {

				entityManager.persist(child);

				assertTrue(entityManager.contains(child));
			}
		}

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		final CriteriaQuery<Event> criteria = entityManager.getCriteriaBuilder().createQuery(
			Event.class
		);

		final Root<Event> items = criteria.from(Event.class);

		criteria.select(items);

		criteria.where(
			entityManager.getCriteriaBuilder().equal(
				items.get(Event_.id),
				events.get(0).getId()
			)
		);


		final List<Event> foundEvents = entityManager.createQuery(
			criteria
		).setHint("org.hibernate.comment", "enableTracing").getResultList();

		assertEquals(foundEvents.size(), 1);

		for (Event event: foundEvents) {
			logger.info("found event: [" + event + "]");

			for (EventChild child: event.getChildren()) {
				logger.info("\tchild: [" + child + "]");

			}

			assertEquals(event.getChildren().size(), 3);
		}

		transaction.commit();

	}


	@org.junit.jupiter.api.Test
	public void testPersistUsersAndPosts() {


		final List<User> users = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			final User user = new User();
			user.setIdentity(java.util.UUID.randomUUID());
			user.setName("user name " + i);
			user.setDateOfBirth(
				LocalDate.of(
					2018,
					(int) ((Math.random() * 12) + 1),
					(int) ((Math.random() * 28) + 1)
				)
			);
			users.add(user);
		}

		final EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		for (User user: users)
			entityManager.persist(user);

		entityManager.flush();
		transaction.commit();


		final List<Post> posts = new ArrayList<>();
		final List<UserPost> userPosts = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			final Post post = new Post();
			post.setIdentity(java.util.UUID.randomUUID());
			post.setTitle("title " + i);
			post.setCreated(LocalDateTime.now());
			post.setContent("content " + i);
			post.setAuthor(users.get(i));

			posts.add(post);

			final UserPost userPost = new UserPost();
			userPost.setId(new UserPostId());
			userPost.getId().setPostIdentity(post.getIdentity());
			userPost.getId().setPostCreated(post.getCreated());
			userPost.getId().setUserIdentity(post.getAuthorIdentity());
			userPost.setPostBasic(post);
			userPost.setPost(post);

			userPosts.add(userPost);
		}

		transaction.begin();

		for (Post post: posts) {
			entityManager.persist(post);
		}

		for (UserPost userPost: userPosts)
			entityManager.persist(userPost);

		entityManager.flush();
		transaction.commit();
		entityManager.clear();


		transaction.begin();

		{
			final Post post = entityManager.find(Post.class, posts.get(0).getIdentity());

			logger.info("post: [" + post + "]");

			assertTrue(post != null);

			final User user = post.getUser();

			logger.info("user: [" + user + "]");

			assertTrue(user != null);

			assertTrue(user.getIdentity().equals(users.get(0).getIdentity()));

		}

		transaction.commit();


		transaction.begin();

		{

			final CriteriaQuery<Post> criteria = entityManager.getCriteriaBuilder().createQuery(
				Post.class
			);

			final Root<Post> items = criteria.from(Post.class);

			criteria.select(items);

			criteria.where(
				entityManager.getCriteriaBuilder().equal(
					items.get(Post_.identity),
					posts.get(0).getIdentity()
				)
			);


			final List<Post> foundPosts = entityManager.createQuery(
				criteria
			).getResultList();
		}

		transaction.commit();

		transaction.begin();

		{
			final User user = entityManager.find(User.class, users.get(0).getIdentity());

			logger.info("user: [" + user + "]");

			assertTrue(user != null);

			logger.info("user.posts: [" + user.getPosts() + "]");

			assertTrue(user.getPosts() != null);
		}

		transaction.commit();
	}

}

