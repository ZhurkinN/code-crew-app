create table public.project
(
    id           bigint       not null primary key generated always as identity,
    leader_id    bigint
        constraint fk_leader_id
            references public.users (id)
            on delete set null
            on update cascade,
    title        varchar(255) not null,
    theme        varchar(255) not null,
    description  varchar(255),
    created_when timestamp             default now(),
    is_deleted   boolean               default false,
    status       varchar(255) not null default 'PREPARING'
        constraint fk_project_status
            references public.dictionary_project_status (status_name)
            on delete set default
            on update cascade
);