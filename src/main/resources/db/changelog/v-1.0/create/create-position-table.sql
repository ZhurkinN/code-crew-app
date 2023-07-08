create table public.position
(
    id bigint not null primary key generated always as identity,
    is_visible boolean default true,
    project_id bigint not null
        constraint fk_project
            references public.project(id)
            on delete cascade
            on update cascade,
    direction varchar(255) not null
        constraint fk_direction
            references public.dictionary_direction(direction_name)
            on delete set null
            on update cascade,
    user_id bigint default null
        constraint fk_user
            references public.users(id)
            on delete set default
            on update cascade,
    description varchar(255) not null,
    skills varchar[] default '{}'::varchar[],
    created_when timestamp default now(),
    is_deleted boolean default false
);