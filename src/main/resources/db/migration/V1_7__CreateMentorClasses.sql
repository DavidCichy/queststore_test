create table mentors_classes
(
    id integer not null
        constraint mentors_classes_pkey
            primary key,
    mentor_id integer,
    class_id integer
);

alter table mentors_classes owner to michal;

