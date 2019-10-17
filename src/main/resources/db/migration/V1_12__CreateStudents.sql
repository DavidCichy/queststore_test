create table students
(
    id serial not null
        constraint codecoolers_pk
            primary key,
    name text not null,
    surname text not null,
    email text not null,
    class_id integer
        constraint class_id
            references "ShopTest".public.classes
            on update cascade on delete cascade,
    user_id integer
);

alter table students owner to michal;

create unique index codecoolers_id_uindex
    on students (id);

