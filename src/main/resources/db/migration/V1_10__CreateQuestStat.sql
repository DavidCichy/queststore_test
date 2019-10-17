create table quests_statuses
(
    id serial not null
        constraint quests_statuses_pkey
            primary key,
    student_id integer,
    quest_id integer,
    reward integer
);

alter table quests_statuses owner to michal;

