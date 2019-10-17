create table users
(
    id       serial  not null
        constraint users_pk
            primary key,
    login    text    not null,
    password text    not null,
    type     integer not null
);

alter table users
    owner to michal;

create unique index users_id_uindex
    on users (id);

create unique index users_login_uindex
    on users (login);
