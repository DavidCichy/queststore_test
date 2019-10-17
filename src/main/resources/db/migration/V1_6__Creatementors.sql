create table mentors
(
    id serial not null
        constraint mentors_pk
            primary key,
    name text not null,
    surname text not null,
    email text not null,
    user_id integer
);

alter table mentors owner to michal;

create unique index mentors_id_uindex
    on mentors (id);

