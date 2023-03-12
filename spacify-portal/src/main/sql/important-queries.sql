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
SELECT * FROM corespacify.incentive;
SELECT * FROM corespacify.monitoring;
SELECT * FROM corespacify.owner;
SELECT * FROM corespacify.subscriber;
SELECT * FROM corespacify.room;
SELECT * FROM corespacify.reservation;

-- Run the below queries for autogenerating roomId
SELECT MAX(room_id) + 1 FROM corespacify.room;
CREATE SEQUENCE room_id_sequence START WITH 11; -- replace '11' with max above
ALTER TABLE corespacify.room ALTER COLUMN room_id SET DEFAULT nextval('room_id_sequence');
ALTER TABLE corespacify.room ALTER COLUMN room_id SET DEFAULT nextval('room_id_sequence'::regclass);
ALTER SEQUENCE room_id_sequence OWNER TO app;

-- ALTER SEQUENCE room_id_sequence RESTART WITH 11;

CREATE OR REPLACE FUNCTION setDefaultRoomId()
RETURNS Trigger AS
$$
BEGIN
NEW.room_id := nextval('room_id_sequence'::regclass);
RETURN NEW;
END;
$$
LANGUAGE PLPGSQL;

CREATE TRIGGER setDefault
BEFORE INSERT ON corespacify.room
FOR EACH ROW
WHEN (NEW.room_id = -1)
EXECUTE FUNCTION setDefaultRoomId();

-- create corespacify.available_slots table
CREATE TABLE IF NOT EXISTS corespacify.available_slots
(
    available_slots_id bigint NOT NULL,
    time_from time without time zone,
    time_to time without time zone,
    room_type text COLLATE pg_catalog."default",
    CONSTRAINT available_slots_pkey PRIMARY KEY (available_slots_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS corespacify.available_slots
    OWNER to app;

