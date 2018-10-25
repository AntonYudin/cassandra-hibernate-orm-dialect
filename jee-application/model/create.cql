
DROP KEYSPACE jee;

CREATE KEYSPACE jee
  WITH REPLICATION = { 
   'class' : 'SimpleStrategy', 
   'replication_factor' : 1
  };
