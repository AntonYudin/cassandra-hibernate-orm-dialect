# cassandra-hibernate-orm-dialect
Hibernate ORM Cassandra Dialect and JDBC Driver

* [Description](README.md#description)
* [Reasons](README.md#reasons)
* [Cassandra model example](README.md#cassandra-model-example)
* [Implementation Details](README.md#implementation-details)
* [Running Tests](README.md#running-tests)

## Description
This is an open-source JDBC driver and Hibernate ORM Dialect for Cassandra.

## Reasons
There are several open source Cassandra JPA implementations out there,
yet none of them support critical features that I often use in my
JPA/JEE projects. For example:

* [Hibernate OGM](https://github.com/hibernate/hibernate-ogm-cassandra)
	* Does not support Criteria API (Not just for Cassandra. No Criteria API support at all)
* [Kundera](https://github.com/Impetus/Kundera)
	* Cassandra scalar native queries return a ``List<Map<String, Object>>`` or ``List<RandomEntity>`` instead of ``List<Object[]>``
	* No JPQL support for ``NEW className(..)`` in ``SELECT`` clause
	* Selecting only ``@EmbeddedId`` property with JPQL fails
	* No proper support for polymorphic queries
	* Criteria API - support for ``@EmbeddedId`` - cannot use ``Path<Entity>`` to access embedded key 
	and lots of other issues of non-JPA compliance

One of the benefits of using JPA is a potential to switch to a
different database provider without changing much of the code. I
wanted to have a complex (not just a numeric ID and a couple of String
attributes) JPA model and a Criteria based queries for that model that
can be deployed to Cassandra, PostgreSQL, and MariaDB.

Here are the requirements for a complex model:

1. Inheritance with support for polymorphic queries
2. OneToMany and ManyToOne relations that can be queried through Criteria API.
3. Composite (Embeddable) primary keys.
4. Support for an Entity that has a primary key that references another Entity.
5. Support for ``java.util.UUID``.
6. Support for an ``@Embeddable`` ID that aggregates another
``@Embeddable`` instance.


The model also has to make sense for a sharded/distributed database.

The ``jee-application/model/src/main/java`` folder contains an example
of a model that works for Cassandra, PostgreSQL, and MariaDB.

## Cassandra model example

Lets take a common example - users and their posts.
One user can have multiple posts. We should be able to:
* search users by their ID.
* search posts by their ID.
* search posts by their ID and the date of the post.
* when we find a post in the previous search, get access to the user ID and name without an extra query
* find all post IDs, titles and creation date posted by a user and do it efficiently - without quering the whole cluster.
* get access to the full post entity after we did a previous search by using an entity property.

here are the tables in cassandra:

```sql
CREATE TABLE jee.users (
    identity uuid PRIMARY KEY,
    dateofbirth date,
    name text
)
```

```sql
CREATE TABLE jee.posts (
    identity uuid PRIMARY KEY,
    author_identity uuid,
    author_name text,
    content text,
    created timestamp,
    title text
)
```

```sql
CREATE TABLE jee.user_posts (
    user_identity uuid,
    post_identity uuid,
    post_created timestamp,
    title text,
    PRIMARY KEY (user_identity, post_identity, post_created)
) WITH CLUSTERING ORDER BY (post_identity ASC, post_created DESC)
```

To design java classes for this model, lets devide the information about a user into two levels - basic (identity, name) and full (identity, name, dateOfBirth). Lets do the same for the posts - basic (identity, created, title) and full (identity, create, title, content). The "full" version of the class inherits the "basic" version.

Here are the classes:
* [UserBasic.java](jee-application/model/src/main/java/com/antonyudin/cassandra/model/users/UserBasic.java)
* [UserFull.java](jee-application/model/src/main/java/com/antonyudin/cassandra/model/users/UserFull.java)
* [PostBasic.java](jee-application/model/src/main/java/com/antonyudin/cassandra/model/users/PostBasic.java)
* [PostFull.java](jee-application/model/src/main/java/com/antonyudin/cassandra/model/users/PostFull.java)

The actual User entity class inherits the Full version and adds a ``@OneToMany`` association with the UserPost entity. The UserPost entity is a de-normalized version of the User-Post association. The UserPost entity has a reference to both User and Post and also contains the Basic information about the Post (created date and title, but no content of the post). The Post has a reference to the user and contains user's Basic information (name). The UserPost entity allows us to find all posts by a user and get the identity, created date, and title of the post in one efficient query.

Here are the classses:
* [User.java](jee-application/model/src/main/java/com/antonyudin/cassandra/model/users/User.java)
* [Post.java](jee-application/model/src/main/java/com/antonyudin/cassandra/model/users/Post.java)
* [UserPost.java](jee-application/model/src/main/java/com/antonyudin/cassandra/model/users/UserPost.java)


Finding a user looks trivial:
```java
final User user = entityManager.find(User.class, identity);
```

Getting all posts with their "Basic" information in one efficient query looks like:
```java
for (UserPost post: user.getPosts()) {
	logger.info("\tpost.id.postIdentity: [" + post.getId().getPostIdentity() + "]");
	logger.info("\tpost.created: [" + post.getPostBasic().getCreated() + "]");
	logger.info("\tpost.title: [" + post.getPostBasic().getTitle() + "]");
}
```

Getting the content for the most recent user post looks like that:
```java
final String content = user.getPosts().get(0).getPost().getContent();
```



## Implementation Details

This repository contains two main parts - Cassandra JDBC driver and
Cassandra Hibernate ORM dialect.

The Cassandra JDBC driver is limited to the functionality that is used
by hibernate. In other words, the JDBC driver
implements the only methods that are used by Hibernate. Surprisingly,
Hibernate uses only a small part of the JDBC API.
This JDBC driver uses DataStax driver to communicate with the Cassandra nodes.

Even though JPA was designed to be used primarily with relational
databases, it still works well
with Cassandra and Cassandra's CQL.
Of course, when designing a JPA model, one should keep in mind the
limitations of Cassandra.

Here are some tips:
1. Use ``@Embeddable`` and ``@EmbeddedId`` to model primary keys. The
order of the fields that are part of the primary
key are important for Cassandra. This implementation does not have a
mechanism to control the order of the fields
in the primary key, but that should not be a problem. After hibernate
generated the schema, one could grab the table
structure using ``describe TABLE`` CQL command and change the order of
the fields in the primary key and re-create
the table.

## Running Tests


