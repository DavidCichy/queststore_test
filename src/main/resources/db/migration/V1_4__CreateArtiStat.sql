create table artifacts_statuses
(
    id serial not null
        constraint artifacts_statuses_pkey
            primary key,
    student_id integer,
    artifact_id integer,
    is_used boolean,
    buy_price integer
);

alter table artifacts_statuses owner to michal;

