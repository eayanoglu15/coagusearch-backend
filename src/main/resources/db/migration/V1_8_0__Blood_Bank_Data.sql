create table bloodbank
(
    key varchar(255)  not null,
    value integer  not null,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone,
    unique (key)
);
insert into bloodbank(key, value, id, version) VALUES ('ffp_Opos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('ffp_Oneg',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('ffp_Apos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('ffp_Aneg',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('ffp_Bpos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('ffp_Bneg',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('ffp_ABpos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('ffp_ABneg',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_Opos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_Oneg',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_Apos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_Aneg',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_Bpos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_Bneg',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_ABpos',1000,nextval('hibernate_sequence'), now()::timestamp);
insert into bloodbank(key, value, id, version) VALUES ('pc_ABneg',1000,nextval('hibernate_sequence'), now()::timestamp);