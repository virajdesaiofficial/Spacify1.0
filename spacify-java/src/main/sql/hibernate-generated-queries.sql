Hibernate:

create table corespacify.mac_address (
                                         mac_address varchar(255) not null,
                                         user_id varchar(255) not null,
                                         primary key (mac_address, user_id)
)
    Hibernate:

create table corespacify.monitoring (
                                        monitoring_id int8 not null,
                                        occupancy int4,
                                        timestamp timestamp,
                                        tippers_space_id int8,
                                        primary key (monitoring_id)
)
    Hibernate:

create table corespacify.owner (
                                   room_id int8 not null,
                                   user_id varchar(255) not null,
                                   primary key (room_id, user_id)
)
    Hibernate:

create table corespacify.reservation (
                                         reservation_id int8 not null,
                                         guests int4,
                                         time_from timestamp,
                                         time_to timestamp,
                                         reserved_room int8,
                                         reserved_by varchar(255),
                                         primary key (reservation_id)
)
    Hibernate:

create table corespacify.room (
                                  room_id int8 not null,
                                  room_rules varchar(255),
                                  room_type varchar(255),
                                  tippers_space_id int8,
                                  primary key (room_id)
)
    Hibernate:

create table corespacify.subscriber (
                                        room_id int8 not null,
                                        user_id varchar(255) not null,
                                        primary key (room_id, user_id)
)
    Hibernate:

create table corespacify.user (
                                  user_id varchar(255) not null,
                                  access_level VARCHAR(20),
                                  email varchar(255),
                                  first_name varchar(255),
                                  last_name varchar(255),
                                  primary key (user_id)
)
    Hibernate:

alter table corespacify.room
drop constraint UK_am0wvxd0vuskim2c1301s6cos
Hibernate:

alter table corespacify.room
    add constraint UK_am0wvxd0vuskim2c1301s6cos unique (tippers_space_id)
    Hibernate:

alter table corespacify.incentive
    add constraint FK9skh2obacjst0hyea8hm8hfq1
        foreign key (user_id)
            references corespacify.user
    Hibernate:

alter table corespacify.mac_address
    add constraint FKifycl9ipifhsr0gv8c2334svy
        foreign key (user_id)
            references corespacify.user
    Hibernate:

alter table corespacify.owner
    add constraint FK57tnvweiivq0dpvc4xg6v1lwf
        foreign key (room_id)
            references corespacify.room
    Hibernate:

alter table corespacify.owner
    add constraint FKsi1e0ouv7mj9eg3ts4buj4wer
        foreign key (user_id)
            references corespacify.user
    Hibernate:

alter table corespacify.reservation
    add constraint FK7bynfqp1wiqocvtnt7127a4x3
        foreign key (reserved_room)
            references corespacify.room
    Hibernate:

alter table corespacify.reservation
    add constraint FKbjmkjnt6mpjnrt49b36xao53w
        foreign key (reserved_by)
            references corespacify.user
    Hibernate:

alter table corespacify.subscriber
    add constraint FKcg6wx6itjn8cbpufk1tjodskx
        foreign key (room_id)
            references corespacify.room
    Hibernate:

alter table corespacify.subscriber
    add constraint FK227ty0ve3wipb6y5i4jvlhcg7
        foreign key (user_id)
            references corespacify.user