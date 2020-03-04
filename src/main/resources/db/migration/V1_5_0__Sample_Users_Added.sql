insert into public.users(id, version, type, identity_number, password)
    values (nextval('hibernate_sequence'), now()::timestamp,
            'Doctor','14051222123','$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, date_of_birth, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select  id from public.users
            where identity_number = '14051222123'),
        'Yunus','Ulas','1968-01-10 00:00:01',178.3,85,'A','Positive','Male');



insert into public.users(id, version, type, identity_number, password)
    values (nextval('hibernate_sequence'), now()::timestamp,
            'Doctor','14051234123','$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, date_of_birth, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select  id from public.users
         where identity_number = '14051234123'),
        'Musa','Açıl','1972-01-10 00:00:01',176.3,78,'B','Positive','Male');


insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor','23244242542','$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, date_of_birth, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select  id from public.users
         where identity_number = '23244242542'),
        'Fatma','Salik','1972-01-10 00:00:01',167.3,68,'0','Positive','Female');


insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor','73647583920','$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, date_of_birth, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select  id from public.users
         where identity_number = '73647583920'),
        'Ayşe','Alip','1978-01-10 00:00:01',165.3,68,'0','Positive','Female');
