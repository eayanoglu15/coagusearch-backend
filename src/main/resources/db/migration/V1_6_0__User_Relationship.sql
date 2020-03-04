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