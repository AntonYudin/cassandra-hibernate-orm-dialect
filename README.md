# cassandra-hibernate-orm-dialect
Hibernate ORM Cassandra Dialect and JDBC Driver

## Description
This is an open-source JDBC driver and Hibernate ORM Dialect for Cassandra.

## Reasons
There are several open source Cassandra JPA implementations out there,
yet none of them support critical features that I often use in my
JPA/JEE projects. For example:

* Hibernate OGM (https://github.com/hibernate/hibernate-ogm-cassandra)
	* Does not support Criteria API (Not just for Cassandra. No Criteria API support at all)
* Kundera (https://github.com/Impetus/Kundera)
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

## Details

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


