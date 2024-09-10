CREATE TABLE public.person (
    person_id bigserial NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    creation_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT person_pk PRIMARY KEY (person_id)
)
TABLESPACE pg_default
;
