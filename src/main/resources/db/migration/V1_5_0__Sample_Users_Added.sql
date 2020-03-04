insert into public.users(id, version, type, identity_number, password)
    values (nextval('hibernate_sequence'), now()::timestamp,
            'Doctor','14051222123','$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');
insert into public.users(id, version, type, identity_number, password)
    values (nextval('hibernate_sequence'), now()::timestamp,
            'Doctor','14051234123','$2a$10$kXpUD3PiDRbaBvvX2tvLnOEh9WU59tGdVBGfUhUj1s3fIll0Tedr2');