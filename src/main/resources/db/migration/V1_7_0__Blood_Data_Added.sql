create table blood_tests
(
    test_name varchar(255)  not null,
    category_name varchar(255)  not null,
    optimum_maximum  double precision,
    optimum_minimum double precision,
    maximum  double precision,
    minimum double precision,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone,
    unique (test_name,category_name)
);

create table user_blood_test
(
    tested_at timestamp,
    user_id bigint,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone,
    unique (user_id,tested_at)
);

create table user_blood_test_data
(
    user_blood_test bigint,
    value double precision,
    blood_test  bigint,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone,
    unique (user_blood_test,blood_test)
);
-----------------------FIBTEM------------------------------------
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('fibtem','CT',69,43,500,30,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('fibtem','CFT',159,34,3000,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('fibtem','Alpha',83,63,100,20,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('fibtem','A10',23,7,50,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('fibtem','A20',24,8,50,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('fibtem','MCF',25,9,40,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('fibtem','LI30',100,94,100,0,nextval('hibernate_sequence'), now()::timestamp);

-----------------------EXTEM------------------------------------
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('extem','CT',79,38,500,30,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('extem','CFT',159,34,3000,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('extem','Alpha',83,63,100,20,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('extem','A10',65,43,100,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('extem','A20',71,50,100,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('extem','MCF',72,50,100,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('extem','LI30',100,94,100,0,nextval('hibernate_sequence'), now()::timestamp);

----------------------INTEM-------------------------
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('intem','CT',240,1000,700,30,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('intem','CFT',110,30,3000,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('intem','Alpha',83,70,100,20,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('intem','A10',66,44,100,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('intem','A20',71,50,100,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('intem','MCF',72,50,100,0,nextval('hibernate_sequence'), now()::timestamp);
insert into blood_tests(test_name, category_name,
                        optimum_maximum, optimum_minimum,
                        maximum, minimum, id, version)
values ('intem','LI30',100,94,100,0,nextval('hibernate_sequence'), now()::timestamp);