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

create table blood_order
(
    patient_id     bigint not null,
    doctor_id  bigint not null,
    blood_type varchar(255),
    rh_type varchar(255),
    product_type varchar(255),
    quantity DOUBLE PRECISION,
    diagnosis varchar(255),
    kind varchar(255),
    bloodTest bigint,
    additional_note varchar(4096),
    unit  varchar(255) not null,
    is_ready boolean not null default false,
    id      bigint NOT NULL primary key,
    version timestamp without time zone
);
ALTER TABLE public.blood_order ALTER COLUMN doctor_id SET NOT NULL;
ALTER TABLE public.blood_order ALTER COLUMN patient_id DROP NOT NULL;
