---------------------------------Doctors-----------------------------------
---Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor', '24343565434', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '24343565434'),
        'Aslı', 'Meski', 10, 1, 1990, 168.3, 65, 'A', 'Positive', 'Female', 280000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '24343565434'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);
---Doctor 2
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Doctor', '98776556776', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '98776556776'),
        'Ahmed', 'Hüsey', 10, 11, 1991, 168.3, 65, 'O', 'Positive', 'Male', 290000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '98776556776'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);




-----------------------------Medical---------------------------
--Medical 1  ---- Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Medical', '56487669878', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '56487669878'),
        'Mehtap', 'Hemka', 10, 1, 1992, 168.3, 65, 'O', 'Positive', 'Female', 311000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '56487669878'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);
insert into doctor_medical_relationship(medical_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '56487669878'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--Medical 2  ---- Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Medical', '38439720987', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '38439720987'),
        'Mehmet', 'Aseki', 10, 1, 1994, 178.3, 75, 'O', 'Positive', 'Male', 230000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '38439720987'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);
insert into doctor_medical_relationship(medical_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '38439720987'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--Medical 3 ---- Doctor 2

insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Medical', '67546329873', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '67546329873'),
        'Ayşe', 'Kılıç', 1, 10, 1996, 168.3, 65, 'O', 'Positive', 'Female', 90000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '67546329873'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);
insert into doctor_medical_relationship(medical_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '67546329873'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--Medical 4 ---- Doctor 2

insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Medical', '23476534598', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '23476534598'),
        'Yunus', 'Karahanlı', 1, 10, 1988, 188.3, 85, 'A', 'Negative', 'Male', 30000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '23476534598'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);
insert into doctor_medical_relationship(medical_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '23476534598'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);


---------------------Patient----------------------------
-----Patient 1  --- Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '87656787098', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '87656787098'),
        'Aycan', 'Candan', 1, 10, 2000, 168.3, 67, 'B', 'Negative', 'Female', 13000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '87656787098'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

-----Patient 2  --- Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '29837492765', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '29837492765'),
        'Zeynep', 'Aslan', 1, 10, 2000, 158.3, 55, 'B', 'Negative', 'Female', 12000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '29837492765'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--------Patient 3  --- Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '67829304856', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '67829304856'),
        'Zehra', 'Aktan', 1, 10, 2007, 167.3, 55, 'A', 'Negative', 'Female', 33000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '67829304856'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--------Patient 4  --- Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '34509864523', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '34509864523'),
        'Zehra', 'Aktan', 1, 10, 2007, 167.3, 55, 'A', 'Negative', 'Female', 73000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '34509864523'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);


--------Patient 5  --- Doctor 1
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '98765723843', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '98765723843'),
        'Ali', 'Candan', 1, 10, 2005, 177.3, 75, 'O', 'Negative', 'Male', 25000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '98765723843'),
        (select id from users where identity_number = '24343565434'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);



--------Patient 1  --- Doctor 2
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '86943213848', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '86943213848'),
        'Ahmet', 'Canda', 1, 10, 2006, 157.3, 45, 'AB', 'Negative', 'Male', 25000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '86943213848'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--------Patient 2  --- Doctor 2
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '78356764519', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '78356764519'),
        'Aysel', 'Aksa', 1, 10, 2005, 157.3, 45, 'B', 'Positive', 'Female', 51000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '78356764519'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--------Patient 3  --- Doctor 2
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '65892367843', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '65892367843'),
        'Melek', 'Sayı', 1, 10, 2013, 157.3, 45, 'A', 'Positive', 'Female', 57000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '65892367843'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);


--------Patient 4  --- Doctor 2
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '92186583710', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '92186583710'),
        'Kemal', 'Kesik', 1, 10, 2012, 157.3, 45, 'O', 'Negative', 'Male', 64000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '92186583710'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);

--------Patient 5  --- Doctor 2
insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '73910928739', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into user_body_info(id, version, user_id,
                           name, surname, birth_day, birth_month, birth_year, height,
                           weight, blood_type, rh_type, gender, platelet_number)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '73910928739'),
        'Seda', 'Elim', 1, 10, 2012, 157.3, 45, 'O', 'Positive', 'Male', 72000);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '73910928739'),
        (select id from users where identity_number = '98776556776'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);




---------------------------------Data of Users---------------------------------------
CREATE OR REPLACE FUNCTION add_user_blood_data(u_identity_number character varying(256),
                                              fibtem_ct double precision,
                                               fibtem_cft double precision,
                                               fibtem_alpha double precision,
                                               fibtem_a10 double precision,
                                               fibtem_a20 double precision,
                                               fibtem_mcf double precision,
                                               fibtem_li30 double precision,
                                               extem_ct double precision,
                                               extem_cft double precision,
                                               extem_alpha double precision,
                                               extem_a10 double precision,
                                               extem_a20 double precision,
                                               extem_mcf double precision,
                                               extem_li30 double precision,
                                               intem_ct double precision,
                                               intem_cft double precision,
                                               intem_alpha double precision,
                                               intem_a10 double precision,
                                               intem_a20 double precision,
                                               intem_mcf double precision,
                                               intem_li30 double precision
                                               )
    RETURNS VOID AS
$$
DECLARE
    user_test_id bigint := nextval('hibernate_sequence');
BEGIN
    insert into user_blood_test(tested_at,user_id,id,version)
    values (now()::timestamp,(select id from users where identity_number = u_identity_number),
            user_test_id,now()::timestamp);




   ---FIBTEM _CT
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
    values (nextval('hibernate_sequence'), now()::timestamp,
            fibtem_ct,
            (select id from blood_tests where test_name = 'fibtem' and category_name = 'CT' limit 1),
            user_test_id);

   ---FIBTEM _CFT
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
    values (nextval('hibernate_sequence'), now()::timestamp,
           fibtem_cft,
           (select id from blood_tests where test_name = 'fibtem' and category_name = 'CFT' limit 1),
           user_test_id);
   ---FIBTEM _ALPHA
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           fibtem_alpha,
           (select id from blood_tests where test_name = 'fibtem' and category_name = 'Alpha' limit 1),
           user_test_id);
   ---FIBTEM _A10
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           fibtem_a10,
           (select id from blood_tests where test_name = 'fibtem' and category_name = 'A10' limit 1),
           user_test_id);
   ---FIBTEM _A20
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           fibtem_a20,
           (select id from blood_tests where test_name = 'fibtem' and category_name = 'A20' limit 1),
           user_test_id);
   ---FIBTEM _MCF
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           fibtem_mcf,
           (select id from blood_tests where test_name = 'fibtem' and category_name = 'MCF' limit 1),
           user_test_id);
   ---FIBTEM _LI30
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
    values (nextval('hibernate_sequence'), now()::timestamp,
           fibtem_li30,
           (select id from blood_tests where test_name = 'fibtem' and category_name = 'LI30' limit 1),
           user_test_id);



   ---EXTEM _CT
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
    values (nextval('hibernate_sequence'), now()::timestamp,
           extem_ct,
           (select id from blood_tests where test_name = 'extem' and category_name = 'CT' limit 1),
           user_test_id);

   ---EXTEM _CFT
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           extem_cft,
           (select id from blood_tests where test_name = 'extem' and category_name = 'CFT' limit 1),
           user_test_id);
   ---EXTEM _ALPHA
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           extem_alpha,
           (select id from blood_tests where test_name = 'extem' and category_name = 'Alpha' limit 1),
           user_test_id);
   ---EXTEM _A10
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           extem_a10,
           (select id from blood_tests where test_name = 'extem' and category_name = 'A10' limit 1),
           user_test_id);
   ---EXTEM _A20
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           extem_a20,
           (select id from blood_tests where test_name = 'extem' and category_name = 'A20' limit 1),
           user_test_id);
   ---EXTEM _MCF
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           extem_mcf,
           (select id from blood_tests where test_name = 'extem' and category_name = 'MCF' limit 1),
           user_test_id);
   ---EXTEM _LI30
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           extem_li30,
           (select id from blood_tests where test_name = 'extem' and category_name = 'LI30' limit 1),
           user_test_id);



   ---INTEM _CT
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           intem_ct,
           (select id from blood_tests where test_name = 'intem' and category_name = 'CT' limit 1),
           user_test_id);

   ---INTEM _CFT
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           intem_cft,
           (select id from blood_tests where test_name = 'intem' and category_name = 'CFT' limit 1),
           user_test_id);
   ---INTEM _ALPHA
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           intem_alpha,
           (select id from blood_tests where test_name = 'intem' and category_name = 'Alpha' limit 1),
           user_test_id);
   ---INTEM _A10
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           intem_a10,
           (select id from blood_tests where test_name = 'intem' and category_name = 'A10' limit 1),
           user_test_id);
   ---INTEM _A20
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           intem_a20,
           (select id from blood_tests where test_name = 'intem' and category_name = 'A20' limit 1),
           user_test_id);
   ---INTEM _MCF
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           intem_mcf,
           (select id from blood_tests where test_name = 'intem' and category_name = 'MCF' limit 1),
           user_test_id);
   ---INTEM _LI30
    insert into user_blood_test_data(id, version, value, blood_test, user_blood_test)
   values (nextval('hibernate_sequence'), now()::timestamp,
           intem_li30,
           (select id from blood_tests where test_name = 'intem' and category_name = 'LI30' limit 1),
           user_test_id);
END;
$$
    LANGUAGE 'plpgsql';

select add_user_blood_data('24343565434',57,285,75,23,23,23,100,61,70,76,63,69,70,null,173,64,77,64,70,71,100);
select add_user_blood_data('98776556776',62,null,69,14,15,16,98,66,78,77,58,64,65,null,181,70,76,57,63,64,100);

select add_user_blood_data('56487669878',61,197,76,22,24,23,100,63,59,79,66,72,72,null,177,51,80,63,68,68,100);
select add_user_blood_data('38439720987',62,null,69,14,15,16,98,66,78,77,58,64,65,null,181,70,76,57,63,64,100);
select add_user_blood_data('67546329873',38,117,78,25,27,28,null,68,2456,84,15,18,17,100,179,267,81,26,33,36,null);
select add_user_blood_data('23476534598',141,3792,64,16,18,20,null,182,297,67,29,39,45,null,442,306,58,28,37,41,null);

select add_user_blood_data('87656787098',68,546,79,20,22,24,100,71,214,78,28,34,37,100,218,281,79,25,31,35,100);
select add_user_blood_data('29837492765',51,null,72,13,14,14,100,74,264,74,29,36,39,100,179,264,72,29,35,39,100);
select add_user_blood_data('67829304856',48,null,null,7,7,7,100,71,379,47,26,34,38,100,95,276,64,30,38,44,100);
select add_user_blood_data('98765723843',54,null,72,10,11,10,100,69,277,69,30,37,42,null,203,432,63,24,32,38,null);
select add_user_blood_data('34509864523',48,null,77,17,18,18,100,62,100,77,47,53,55,100,129,89,78,49,55,57,100);


select add_user_blood_data('86943213848',48,4226,73,16,18,19,100,67,195,73,34,41,42,100,188,187,74,34,40,40,100);
select add_user_blood_data('78356764519',479,null,null,3,3,3,78,190,null,null,4,3,5,70,663,null,2,4,4,4,100);
select add_user_blood_data('65892367843',212,null,null,9,12,13,100,279,296,43,33,43,48,100,430,165,60,41,49,53,100);
select add_user_blood_data('92186583710',48,null,77,17,18,18,100,62,100,77,47,53,55,100,129,89,78,49,55,57,100);
select add_user_blood_data('73910928739',56,null,70,13,14,14,100,54,132,74,41,48,50,100,162,148,67,39,46,47,100);