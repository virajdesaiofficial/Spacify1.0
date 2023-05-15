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


-- Adding calculated flag column in Reservation table
ALTER TABLE corespacify.reservation ADD COLUMN calculated_flag BOOLEAN NOT NULL DEFAULT FALSE;

-- Adding total incentive column in User table
ALTER TABLE corespacify.user ADD COLUMN total_incentives int8 DEFAULT 0;

-- Run the below queries for auto generating incentiveId
SELECT MAX(incentive_id) + 1 FROM corespacify.incentive;
CREATE SEQUENCE incentive_id_sequence START WITH 8; -- replace '8' with max above
ALTER TABLE corespacify.incentive ALTER COLUMN incentive_id SET DEFAULT nextval('room_id_sequence'::regclass);
ALTER SEQUENCE incentive_id_sequence OWNER TO app;

-- ALTER SEQUENCE incentive_id_sequence RESTART WITH 8;

CREATE OR REPLACE FUNCTION setDefaultIncentiveId()
RETURNS Trigger AS
$$
BEGIN
NEW.incentive_id := nextval('incentive_id_sequence'::regclass);
RETURN NEW;
END;
$$
LANGUAGE PLPGSQL;

CREATE TRIGGER setDefaultIncentive
BEFORE INSERT ON corespacify.incentive
FOR EACH ROW
WHEN (NEW.incentive_id = -1)
EXECUTE FUNCTION setDefaultIncentiveId();

-- altering monitoring table
ALTER TABLE corespacify.monitoring RENAME COLUMN timestamp TO timestamp_from;

ALTER TABLE corespacify.monitoring ADD COLUMN timestamp_to timestamp NOT NULL;

-- altering the room_rules column type to allow larger json. May have to use json type for better usage.
-- ALTER TABLE corespacify.room ALTER COLUMN room_rules TYPE json USING room_rules::json;

ALTER TABLE corespacify.room ALTER COLUMN room_rules TYPE text;

-- alter incentive timestamp column
alter table corespacify.incentive alter column timestamp type TIMESTAMP without time zone;
ALTER TABLE corespacify.incentive ALTER COLUMN timestamp SET NOT NULL;


ALTER TABLE corespacify.user ADD COLUMN total_incentives int8 DEFAULT 0;



SELECT MAX(monitoring_id) + 1 FROM corespacify.monitoring;
CREATE SEQUENCE monitoring_id_sequence START WITH 12;
ALTER TABLE corespacify.monitoring ALTER COLUMN monitoring_id SET DEFAULT nextval('monitoring_id_sequence'::regclass);
ALTER SEQUENCE monitoring_id_sequence OWNER TO app;

CREATE OR REPLACE FUNCTION setDefaultMonitoringId()
RETURNS Trigger AS
$$
BEGIN
NEW.monitoring_id := nextval('monitoring_id_sequence'::regclass);
RETURN NEW;
END;
$$
LANGUAGE PLPGSQL;

CREATE TRIGGER setDefaultMonitoring
BEFORE INSERT ON corespacify.monitoring
FOR EACH ROW
WHEN (NEW.monitoring_id = -1)
EXECUTE FUNCTION setDefaultMonitoringId();


---------------------------------------------------------------------
-- IMPORTANT UPDATE. drop the current incentive table and create using these queries. To enable auto generating ids. The trigger was creating issues.
CREATE table corespacify.incentive(
                                      incentive_id bigserial primary key,
                                      incentive_points int8 not null,
                                      timestamp TIMESTAMP without time zone not null,
                                      user_id VARCHAR(255) not null,
                                      added boolean not null
)

alter table corespacify.incentive
    add constraint FK9skh2obacjst0hyea8hm8hfq1
        foreign key (user_id)
            references corespacify.user

-- Now insert mock data into this using the Insert queries in mock-data.sql. You do not need to specify primary key in inserts. DB will handle that.

-- authentication table for storing user passwords
CREATE table corespacify.authentication(
                                           user_id VARCHAR(255) primary key,
                                           hashed_password VARCHAR(128) not null,
                                           constraint auth_user foreign key (user_id) references corespacify.user(user_id)
);

-- adding columns for verification email and codes! We also want to make email unique so that each email is linked to only one account
ALTER TABLE corespacify.user ADD COLUMN verified boolean DEFAULT false;

ALTER TABLE corespacify.user ADD COLUMN verification_code text;

ALTER TABLE corespacify.user ADD UNIQUE(email);

