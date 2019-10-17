create table artifacts_categories
(
    id serial not null
        constraint artifacts_categories_pkey
            primary key,
    category text
);

alter table artifacts_categories owner to michal;

