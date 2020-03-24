insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor', '14051222123', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, birth_day, birth_month, birth_year, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '14051222123'),
        'Yunus', 'Ulas', 10, 1, 1968, 178.3, 85, 'A', 'Positive', 'Male');



insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor', '14051234123', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, birth_day, birth_month, birth_year, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '14051234123'),
        'Muharrem', 'Salel', 10, 1, 1998, 179.3, 78, 'A', 'Positive', 'Male');


insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor', '23244242542', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, birth_day, birth_month, birth_year, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '23244242542'),
        'Fatma', 'Salik', 10, 5, 1972, 167.3, 68, '0', 'Positive', 'Female');


insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor', '73647583920', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, birth_day, birth_month, birth_year, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '73647583920'),
        'Ayşe', 'Alip', 21, 11, 1978, 165.3, 68, '0', 'Positive', 'Female');
