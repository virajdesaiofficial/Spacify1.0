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
-- updated queries without setting primary key

INSERT INTO corespacify.incentive(incentive_points, timestamp, user_id, added) VALUES(10, '2023-02-23 02:00:00', 'sanchg4', false);
INSERT INTO corespacify.incentive(incentive_points, timestamp, user_id, added) VALUES(10, '2023-02-23 02:00:02', 'desaivh', false);
INSERT INTO corespacify.incentive(incentive_points, timestamp, user_id, added) VALUES(15, '2023-02-23 02:00:20', 'dmohanku',false);
INSERT INTO corespacify.incentive(incentive_points, timestamp, user_id, added) VALUES(5, '2023-02-21 02:00:00', 'hthallam', false);
INSERT INTO corespacify.incentive(incentive_points, timestamp, user_id, added) VALUES(10, '2023-01-29 02:00:00', 'sdharank', false);
INSERT INTO corespacify.incentive(incentive_points, timestamp, user_id, added) VALUES(15, '2023-02-04 02:00:00', 'msrahma2', false);
INSERT INTO corespacify.incentive(incentive_points, timestamp, user_id, added) VALUES(15, '2023-02-19 02:00:00', 'desaivh', false);



-- available slots entries
INSERT INTO corespacify.available_slots(available_slots_id, time_from, time_to, room_type) VALUES (1, '08:00:00', '00:00:00', 'STUDY');
INSERT INTO corespacify.available_slots(available_slots_id, time_from, time_to, room_type) VALUES (2, '10:00:00', '12:00:00', 'OFFICE');
INSERT INTO corespacify.available_slots(available_slots_id, time_from, time_to, room_type) VALUES (3, '10:00:00', '20:00:00', 'COMMON_SPACE');

-- mac address table entries

INSERT INTO corespacify.mac_address VALUES('12.13.14.15', 'desaivh');
INSERT INTO corespacify.mac_address VALUES('10.12.12.12', 'desaivh');
INSERT INTO corespacify.mac_address VALUES('AA.1B.F4.15', 'desaivh');
INSERT INTO corespacify.mac_address VALUES('12.16.17.AB', 'sanchg4');
INSERT INTO corespacify.mac_address VALUES('13.13.14.13', 'sanchg4');
INSERT INTO corespacify.mac_address VALUES('AD.13.19.03', 'dmohanku');
INSERT INTO corespacify.mac_address VALUES('09.22.16.13', 'sdharank');
INSERT INTO corespacify.mac_address VALUES('FF.10.AF.13', 'sdharank');
INSERT INTO corespacify.mac_address VALUES('18.19.A4.13', 'hthallam');
INSERT INTO corespacify.mac_address VALUES('17.13.14.EE', 'hthallam');
INSERT INTO corespacify.mac_address VALUES('18.9A.14.EF', 'msrahma2');

-- monitoring table entries

INSERT INTO corespacify.monitoring VALUES(1, 5, '2023-02-18 10:00:00', 24, '10.12.12.12', '2023-02-18 10:03:00');
INSERT INTO corespacify.monitoring VALUES(2, 6, '2023-02-18 10:03:00', 24, '10.12.12.12', '2023-02-18 12:00:00');
INSERT INTO corespacify.monitoring VALUES(3, 6, '2023-02-18 12:00:00', 24, '10.12.12.12', '2023-02-18 12:30:00');
INSERT INTO corespacify.monitoring VALUES(4, 6, '2023-02-18 12:40:00', 24, '10.12.12.12', '2023-02-18 1:00:00');
INSERT INTO corespacify.monitoring VALUES(5, 6, '2023-02-18 1:00:00', 24, '10.12.12.12', '2023-02-18 14:00:00');
INSERT INTO corespacify.monitoring VALUES(6, 5, '2023-02-18 14:00:00', 24, '10.12.12.12', '2023-02-18 14:15:00');
INSERT INTO corespacify.monitoring VALUES(7, 7, '2023-02-22 00:00:00', 12, 'AD.13.19.03', '2023-02-22 00:10:00');
INSERT INTO corespacify.monitoring VALUES(8, 7, '2023-02-22 00:10:00', 12, 'AD.13.19.03', '2023-02-22 00:30:00');
INSERT INTO corespacify.monitoring VALUES(9, 7, '2023-02-22 00:35:00', 12, 'AD.13.19.03', '2023-02-22 01:00:00');
INSERT INTO corespacify.monitoring VALUES(10, 7, '2023-02-22 01:00:00', 12, 'AD.13.19.03', '2023-02-22 01:45:00');
INSERT INTO corespacify.monitoring VALUES(11, 19, '2023-02-20 19:00:00', 12, '18.9A.14.EF', '2023-02-20 20:15:00');
