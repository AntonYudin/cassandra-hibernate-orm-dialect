
DROP TABLE events;
DROP TABLE event_children;
DROP TABLE children;
DROP TABLE user_posts;

CREATE TABLE event_children (source_identity_value uuid, child_id bigint, identity uuid, type text, groupname text, reference bigint, created timestamp, PRIMARY KEY (source_identity_value, identity, type, groupname, reference, created, child_id));

CREATE TABLE events (source_identity uuid, created timestamp, groupName TEXT, identity uuid, reference bigint, type TEXT, doubleValue double, content TEXT, content2 TEXT, data blob, floatValue float, listofnames list<text>, mapofvalues map<text, text>, removed boolean, shortValue smallint, name TEXT, createdLocalDateTime timestamp, createdLocalDate date, createdLocalTime time, primary key (source_identity, identity, type, groupName, reference, created));

CREATE TABLE children (child_id bigint, parent_id bigint, PRIMARY KEY (parent_id, child_id));

CREATE INDEX events_index_content on events (content);

CREATE CUSTOM INDEX events_index_content2 ON events (content2) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = { 'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.NonTokenizingAnalyzer', 'case_sensitive': 'false', 'mode': 'CONTAINS'};

CREATE TABLE jee.user_posts (user_identity uuid, post_created timestamp, post_identity uuid, title text, PRIMARY KEY (user_identity, post_created, post_identity)) WITH CLUSTERING ORDER BY (post_created DESC, post_identity ASC);

