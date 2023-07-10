create table public.project_members
(
    user_id    bigint not null
        constraint fk_users_project_members
            references public.users (id)
            on delete cascade
            on update cascade,
    project_id bigint not null
        constraint fk_project_project_members
            references public.project (id)
            on delete cascade
            on update cascade,

    primary key (user_id, project_id)
)