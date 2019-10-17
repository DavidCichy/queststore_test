create table sessions
(
    id serial not null
        constraint sessions_pkey
            primary key,
    user_id integer,
    session_id text
);

alter table sessions owner to michal;

