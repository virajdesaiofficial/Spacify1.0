CREATE SCHEMA IF NOT EXISTS corespacify;

CREATE TYPE access_type AS ENUM ('admin', 'student', 'faculty');

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA corespacify TO app;

GRANT ALL ON ALL SEQUENCES IN SCHEMA corespacify TO app;

GRANT ALL PRIVILEGES ON SCHEMA corespacify TO app;

-- // If you have created manually, you'll need to give 'app' user the ownership of the table. Else hibernate will not eb able to alter or use it'
ALTER TABLE corespacify.room OWNER TO app;

-- All the tables in database
SELECT * FROM corespacify.user;
SELECT * FROM corespacify.mac_address;
SELECT * FROM corespacify.incentive;/
SELECT * FROM corespacify.monitoring;
SELECT * FROM corespacify.owner;
SELECT * FROM corespacify.subscriber;
SELECT * FROM corespacify.room;
SELECT * FROM corespacify.reservation;