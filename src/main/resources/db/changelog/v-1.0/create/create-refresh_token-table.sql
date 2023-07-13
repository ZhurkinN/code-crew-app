create table public.refresh_token
(
    id            bigint primary key generated always as identity,
    refresh_token varchar(21844) not null,
    username      varchar(255)   not null,
    date_created  timestamp default now(),
    revoked       boolean
);