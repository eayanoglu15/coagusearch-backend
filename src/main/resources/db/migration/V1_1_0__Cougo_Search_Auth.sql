CREATE TABLE public.refresh_token (
           id bigint NOT NULL,
           version timestamp without time zone,
           created_at timestamp without time zone NOT NULL,
           updated_at timestamp without time zone NOT NULL,
           refresh_token character varying(255),
           user_id bigint NOT NULL
);

CREATE TABLE public.users (
         id bigint NOT NULL,
         version timestamp without time zone,
         type character varying(255),
         identity_number character varying(255) NOT NULL,
         password character varying(4096) NOT NULL
);
ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_unique_identity_number UNIQUE (identity_number);
CREATE TABLE public.multi_language_string (
         id bigint NOT NULL,
         key character varying(255),
         tr_string character varying(4096),
         en_string character varying(4096)
);


ALTER TABLE ONLY public.multi_language_string
    ADD CONSTRAINT multi_language_string_unique_key UNIQUE (key);

