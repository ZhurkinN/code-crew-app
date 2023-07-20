create table public.users
(
    id               bigint       not null
        primary key generated always as identity,
    email            varchar(255),
    name             varchar(255) not null,
    surname          varchar(255) not null,
    password         varchar(255) not null,
    picture_link     varchar(255),
    main_information varchar(255),
    contacts         varchar[] default '{}'::varchar[],
    created_when     timestamp default now(),
    is_deleted       boolean   default false
);