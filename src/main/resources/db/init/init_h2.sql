create table if not exists refresh_token_entity
(
    id bigint primary key not null,
    refresh_token varchar(21844) not null,
    username      varchar(255)        not null,
    date_create   timestamp default now(),
    revoked       boolean
);