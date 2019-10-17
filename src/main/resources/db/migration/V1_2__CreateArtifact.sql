create table artifacts
(
    id serial not null
        constraint artifacts_pk
            primary key,
    name text not null,
    description text not null,
    price integer not null,
    category integer
);

alter table artifacts owner to michal;

create unique index artifacts_id_uindex
    on artifacts (id);

