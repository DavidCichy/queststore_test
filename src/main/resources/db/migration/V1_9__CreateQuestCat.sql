create table quests_categories
(
    id serial not null
        constraint quests_categories_pkey
            primary key,
    category text
);

alter table quests_categories owner to michal;

