create table quests
(
    id serial not null
        constraint quests_pk
            primary key,
    name text not null,
    description text not null,
    reward integer not null,
    category integer not null
);

alter table quests owner to michal;

create unique index quests_id_uindex
    on quests (id);

