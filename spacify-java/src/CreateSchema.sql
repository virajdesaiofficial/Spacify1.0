CREATE SCHEMA IF NOT EXISTS coreSpacify;

CREATE TYPE access_type AS ENUM ('admin', 'student', 'faculty');

CREATE TABLE coreSpacify.User (
                                  userId text,
                                  email text NOT NULL,
                                  firstName text NOT NULL,
                                  lastName text,
                                  access_level access_type,
                                  PRIMARY KEY(userId),
                                  UNIQUE(email)
);

CREATE TABLE coreSpacify.MacAddress (
                                        userId text NOT NULL,
                                        macAddress text NOT NULL,
                                        PRIMARY KEY(userId, macAddress),
                                        FOREIGN KEY(userId) REFERENCES coreSpacify.User(userId)
);

INSERT INTO corespacify.User(userId, email, firstName) VALUES ('desaivh', '@gmail.com','viraj');