create table classes
(
    id serial not null
        constraint classes_pk
            primary key,
    type text not null
);

alter table classes owner to michal;

create unique index classes_id_uindex
    on classes (id);

