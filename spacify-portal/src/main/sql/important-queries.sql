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

-- INSERT INTO corespacify.user VALUES('desaivh', 'admin', 'desaivh@uci.edu', 'Viraj', 'Desai');
-- SELECT * FROM corespacify.user;

-- INSERT INTO corespacify.room VALUES(1, 'something', 'study', 12);
-- SELECT * FROM corespacify.room;

-- INSERT INTO corespacify.reservation VALUES(1, 8, '2023-2-22'::timestamp, now(), 1, 'desaivh');
-- SELECT * FROM corespacify.reservation;

-- DELETE FROM corespacify.reservation;