create table doctor_patient_relationship
(
    patient_id     bigint not null,
    doctor_id  bigint not null,
    active bool default true,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone
);
ALTER TABLE ONLY public.doctor_patient_relationship
    add constraint foreign_key_doctor_patient_relationship_doctors foreign key (doctor_id)
        references public.users (id),
    add constraint foreign_key_doctor_patient_relationship_patient foreign key (patient_id)
        references public.users (id);


create table doctor_medical_relationship
(
    medical_id     bigint not null,
    doctor_id  bigint not null,
    active bool default true,
    id      bigint NOT NULL primary key ,
    version timestamp without time zone
);
ALTER TABLE ONLY public.doctor_medical_relationship
    add constraint foreign_key_doctor_medical_relationship_doctors foreign key (doctor_id)
        references public.users (id),
    add constraint foreign_key_doctor_medical_relationship_medical foreign key (medical_id)
        references public.users (id);


insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Medical', '5678765678', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, birth_day, birth_month, birth_year, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '14051222123'),
        'Ali', 'Candan', 10, 1, 1968, 178.3, 85, 'A', 'Positive', 'Male');

insert into public.users(id, version, type, identity_number, password)
values (nextval('hibernate_sequence'), now()::timestamp,
        'Patient', '987656789', '$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.user_body_info(id, version, user_id,
                                  name, surname, birth_day, birth_month, birth_year, height,
                                  weight, blood_type, rh_type, gender)
values (nextval('hibernate_sequence'), now()::timestamp,
        (select id
         from public.users
         where identity_number = '14051222123'),
        'Polat', 'Alemdar', 10, 1, 1968, 178.3, 85, 'A', 'Positive', 'Male');

insert into doctor_medical_relationship(medical_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '5678765678'),
        (select id from users where identity_number = '14051234123'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);
insert into doctor_patient_relationship(patient_id, doctor_id, active, id, version)
values ((select id from users where identity_number = '14051222123'),
        (select id from users where identity_number = '14051234123'),
        true,
        nextval('hibernate_sequence'), now()::timestamp);