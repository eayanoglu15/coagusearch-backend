create table drug_frequency
(
    key     varchar(255),
    detail  bigint not null,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone
);
ALTER TABLE ONLY public.drug_frequency
    add constraint foreign_key_drug_frequency_multi_language_string foreign key (detail)
        references public.multi_language_string (id);


create table user_regular_medicine
(
    user_id    bigint not null,
    mode   varchar(255) not null,
    drug_key   bigint,
    frequency   bigint,
    dosage   double precision,
    custom varchar(255),
    active bool,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone
);
ALTER TABLE ONLY public.user_regular_medicine
    add constraint foreign_key_user_regular_medicine_drug_info foreign key (drug_key)
        references public.drug_info (id),
    add constraint foreign_key_user_regular_medicine_frequency foreign key (frequency)
        references public.drug_frequency (id),
    add constraint foreign_key_user_regular_medicine_users foreign key (user_id)
        references public.users (id);

insert into public.multi_language_string(id, version, key, tr_string, en_string)
    values (nextval('hibernate_sequence'), now()::timestamp, 'regular_med_once','günde bir defa' ,'once a day');
insert into public.multi_language_string(id, version, key, tr_string, en_string)
    values (nextval('hibernate_sequence'), now()::timestamp, 'regular_med_twice','günde iki defa' ,'twice a day');
insert into public.multi_language_string(id, version, key, tr_string, en_string)
    values (nextval('hibernate_sequence'), now()::timestamp, 'regular_med_third','günde üç defa' ,'third times a day');
insert into public.multi_language_string(id, version, key, tr_string, en_string)
    values (nextval('hibernate_sequence'), now()::timestamp, 'regular_med_fourth','günde dört defa' ,'four times a day');
insert into public.multi_language_string(id, version, key, tr_string, en_string)
    values (nextval('hibernate_sequence'), now()::timestamp, 'regular_med_needed','gerektiğinde' ,'as needed');

insert into public.drug_frequency(id, version, key, detail)
    values (nextval('hibernate_sequence'), now()::timestamp, 'once',
            (select id from multi_language_string where key = 'regular_med_once'));
insert into public.drug_frequency(id, version, key, detail)
    values (nextval('hibernate_sequence'), now()::timestamp, 'twice',
            (select id from multi_language_string where key = 'regular_med_twice'));
insert into public.drug_frequency(id, version, key, detail)
    values (nextval('hibernate_sequence'), now()::timestamp, 'third',
            (select id from multi_language_string where key = 'regular_med_third'));
insert into public.drug_frequency(id, version, key, detail)
    values (nextval('hibernate_sequence'), now()::timestamp, 'fourth',
            (select id from multi_language_string where key = 'regular_med_fourth'));
insert into public.drug_frequency(id, version, key, detail)
    values (nextval('hibernate_sequence'), now()::timestamp, 'needed',
            (select id from multi_language_string where key = 'regular_med_needed'));