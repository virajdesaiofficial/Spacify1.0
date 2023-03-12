-- user table entries
INSERT INTO corespacify.user VALUES('desaivh', 'admin', 'desaivh@uci.edu', 'Viraj', 'Desai');
INSERT INTO corespacify.user VALUES('sanchg4', 'admin', 'sanchg4@uci.edu', 'Sanchi', 'Gupta');
INSERT INTO corespacify.user VALUES('sdharank', 'admin', 'sdharank@uci.edu', 'Samruddhi', 'Dharankar');
INSERT INTO corespacify.user VALUES('hthallam', 'student', 'hthallam@uci.edu', 'Himanshu');
INSERT INTO corespacify.user VALUES('msrahma2', 'student', 'msrahma2@uci.edu', 'Saqib', 'Rahman');
INSERT INTO corespacify.user VALUES('acolaco', 'faculty', 'acolaco@uci.edu', 'Ashwin', 'Colaco');
INSERT INTO corespacify.user VALUES('sshekoka', 'student', 'sshekoka@uci.edu', 'Sarang', 'Shekokar');
INSERT INTO corespacify.user VALUES('diazvi', 'student', 'diazvi@uci.edu', 'Victor');
INSERT INTO corespacify.user VALUES('kshatris', 'student', 'kshatris@uci.edu', 'Sarthak');
INSERT INTO corespacify.user VALUES('chinmank', 'student', 'chinmank@uci.edu', 'Chinmay', 'Kulkarni');
INSERT INTO corespacify.user VALUES('dmohanku', 'student', 'dmohanku@uci.edu', 'DJ', 'Kumar');
INSERT INTO corespacify.user VALUES('sharad', 'faculty', 'sharad@ics.uci.edu', 'Sharad', 'Mehrotra');

UPDATE corespacify.user SET access_level='STUDENT' WHERE access_level='student';
UPDATE corespacify.user SET access_level='ADMIN' WHERE access_level='admin';
UPDATE corespacify.user SET access_level='FACULTY' WHERE access_level='faculty';

-- room table entries

INSERT INTO corespacify.room VALUES(1, 'something', 'study', 12);
INSERT INTO corespacify.room VALUES(2, '', 'study', 55);
INSERT INTO corespacify.room VALUES(3, 'something else', 'study', 5);
INSERT INTO corespacify.room VALUES(4, null, 'office', 9);
INSERT INTO corespacify.room VALUES(5, null, 'office', 80);
INSERT INTO corespacify.room VALUES(6, null, 'common space', 70);
INSERT INTO corespacify.room VALUES(7, '', 'study', 20);
INSERT INTO corespacify.room VALUES(8, 'no more than 10 people', 'conference', 24);
INSERT INTO corespacify.room VALUES(9, 'blah blah blah', 'study', 23);
INSERT INTO corespacify.room VALUES(10, null, 'study', 17);

ALTER TABLE corespacify.room ADD COLUMN description VARCHAR(255);
UPDATE corespacify.room SET description = 'C110 VP' WHERE room_id=1;
UPDATE corespacify.room SET description = 'B100 VP' WHERE room_id=2;
UPDATE corespacify.room SET description = 'A100 VP' WHERE room_id=3;
UPDATE corespacify.room SET description = 'CC102' WHERE room_id=4;
UPDATE corespacify.room SET description = 'CC104' WHERE room_id=5;
UPDATE corespacify.room SET description = 'CC Reception' WHERE room_id=6;
UPDATE corespacify.room SET description = 'C111 VP' WHERE room_id=7;
UPDATE corespacify.room SET description = 'CC20' WHERE room_id=8;
UPDATE corespacify.room SET description = 'B101 VP' WHERE room_id=9;
UPDATE corespacify.room SET description = 'D100 VP' WHERE room_id=10;

UPDATE corespacify.room SET room_type = 'STUDY' WHERE room_type='study';
UPDATE corespacify.room SET room_type = 'CONFERENCE' WHERE room_type='conference';
UPDATE corespacify.room SET room_type = 'OFFICE' WHERE room_type='office';
UPDATE corespacify.room SET room_type = 'COMMON_SPACE' WHERE room_type='common space';

-- owner table entries

INSERT INTO corespacify.owner VALUES(1, 'desaivh');
INSERT INTO corespacify.owner VALUES(1, 'sdharank');
INSERT INTO corespacify.owner VALUES(5, 'acolaco');
INSERT INTO corespacify.owner VALUES(8, 'sanchg4');
INSERT INTO corespacify.owner VALUES(4, 'sharad');

-- subscriber table entries

INSERT INTO corespacify.subscriber VALUES(1, 'sanchg4');
INSERT INTO corespacify.subscriber VALUES(1, 'sshekoka');
INSERT INTO corespacify.subscriber VALUES(1, 'kshatris');
INSERT INTO corespacify.subscriber VALUES(8, 'chinmank');
INSERT INTO corespacify.subscriber VALUES(8, 'dmohanku');
INSERT INTO corespacify.subscriber VALUES(2, 'hthallam');
INSERT INTO corespacify.subscriber VALUES(2, 'acolaco');
INSERT INTO corespacify.subscriber VALUES(8, 'desaivh');

-- reservation table entries

INSERT INTO corespacify.reservation VALUES(1, 8, '2023-02-22 00:00:00', '2023-02-22 19:13:29.36106', 7, 'desaivh');
INSERT INTO corespacify.reservation VALUES(2, 4, '2023-02-22 11:00:00', '2023-02-22 14:00:00', 2, 'sanchg4');
INSERT INTO corespacify.reservation VALUES(3, 5, '2023-02-20 12:00:00', '2023-02-20 13:00:00', 9, 'hthallam');
INSERT INTO corespacify.reservation VALUES(5, 2, '2023-01-28 00:00:00', '2028-01-28 01:30:00', 9, 'sdharank');
INSERT INTO corespacify.reservation VALUES(6, 15, '2023-02-03 15:30:00', '2023-02-20 18:00:00', 1, 'msrahma2');
INSERT INTO corespacify.reservation VALUES(7, null, '2023-02-22 00:00:00', '2023-02-20 18:00:00', 1, 'dmohanku');
INSERT INTO corespacify.reservation VALUES(8, null, '2023-02-18 10:00:00', '2023-02-20 14:00:00', 8, 'desaivh');

-- incentive table entries

INSERT INTO corespacify.incentive VALUES(1, 10, '2023-02-23 02:00:00', 'sanchg4');
INSERT INTO corespacify.incentive VALUES(2, 10, '2023-02-23 02:00:02', 'desaivh');
INSERT INTO corespacify.incentive VALUES(3, 15, '2023-02-23 02:00:20', 'dmohanku');
INSERT INTO corespacify.incentive VALUES(4, 5, '2023-02-21 02:00:00', 'hthallam');
INSERT INTO corespacify.incentive VALUES(5, 10, '2023-01-29 02:00:00', 'sdharank');
INSERT INTO corespacify.incentive VALUES(6, 15, '2023-02-04 02:00:00', 'msrahma2');
INSERT INTO corespacify.incentive VALUES(7, 15, '2023-02-19 02:00:00', 'desaivh');

-- monitoring table entries


-- available slots entries
INSERT INTO corespacify.available_slots(available_slots_id, time_from, time_to, room_type) VALUES (1, '08:00:00', '00:00:00', 'STUDY');
INSERT INTO corespacify.available_slots(available_slots_id, time_from, time_to, room_type) VALUES (2, '10:00:00', '12:00:00', 'OFFICE');
INSERT INTO corespacify.available_slots(available_slots_id, time_from, time_to, room_type) VALUES (3, '10:00:00', '20:00:00', 'COMMON_SPACE');

-- TODO: finalize the table's structure based on the final API contract.
