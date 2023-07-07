create table public.project_information
(
    id bigint not null primary key generated always as identity,
    project_id bigint not null
        constraint fk_project
            references public.project(id)
            on delete cascade
            on update cascade,
    link varchar not null,
    description varchar not null
);