create table public.resume
(
    id           bigint       not null
        primary key generated always as identity,
    description  varchar(255),
    is_active    boolean   default true,
    created_when timestamp default now(),
    is_deleted   boolean   default false,
    direction    varchar(255) not null
        constraint fk_direction
            references public.dictionary_direction (direction_name)
            on delete set null
            on update cascade,
    user_id      bigint       not null
        constraint fk_user_id
            references public.users
            on delete cascade
            on update cascade,
    skills       varchar[] default '{}'::varchar[]
);