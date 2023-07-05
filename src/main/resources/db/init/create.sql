create sequence if not exists public.position_seq
    increment by 50;

create sequence if not exists public.resume_seq
    increment by 50;

create sequence if not exists public.team_request_seq
    increment by 50;

create sequence if not exists public.project_seq
    increment by 50;

create sequence if not exists public.users_seq
    increment by 50;

create sequence if not exists public.project_information_seq
    increment by 50;

create table if not exists public.dictionary_direction
(
    direction_name varchar(255) not null
        primary key,
    description varchar(255) not null
);

create table if not exists public.dictionary_project_status
(
    status_name varchar(255) not null
        primary key,
    description varchar(255) not null
);

create table if not exists public.dictionary_request_status
(
    status_name varchar(255) not null
        primary key,
    description varchar(255) not null
);

create table if not exists public.users
(
    id           bigint not null
        primary key,
    email        varchar(255),
    name    varchar(255) not null,
    surname    varchar(255) not null,
    password     varchar(255) not null,
    picture_link varchar(255),
    main_information varchar(255) not null,
    contacts varchar[],
    created_when timestamp default now(),
    is_deleted boolean default false
);

create table if not exists public.resume
(
    id           bigint not null
        primary key,
    description  varchar(255),
    is_active    boolean default true,
    created_when timestamp default now(),
    is_deleted boolean default false,
    direction varchar(255) not null
        constraint fk_direction
            references public.dictionary_direction(direction_name)
            on delete set null
            on update cascade,
    user_id      bigint not null
        constraint fk_user_id
            references public.users
            on delete cascade
            on update cascade,
    skills varchar[],

    unique (user_id, direction)
);

create table if not exists public.project
(
    id bigint not null primary key,
    leader_id bigint
        constraint fk_leader_id
            references public.users(id)
            on delete set null
            on update cascade,
    title varchar(255) not null,
    theme varchar(255) not null,
    description varchar(255),
    created_when timestamp default now(),
    is_deleted boolean default false,
    status varchar(255) not null default 'PREPARING'
        constraint fk_project_status
            references public.dictionary_project_status(status_name)
            on delete set default
            on update cascade
);

create table public.position
(
    id bigint not null primary key,
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
    skills varchar[],
    created_when timestamp default now(),
    is_deleted boolean default false
);


create table if not exists public.position_request
(
    id bigint not null primary key,
    resume_id bigint not null
        constraint fk_resume
            references public.resume(id)
            on delete cascade
            on update cascade,
    position_id bigint not null
        constraint fk_positions
            references public.position(id)
            on delete cascade
            on update cascade,
    status varchar(255) default 'IN_CONSIDERATION'
        constraint fk_request_status
            references public.dictionary_request_status(status_name)
            on delete set default
            on update cascade,
    cover_letter varchar(255),
    created_when timestamp default now(),
    is_deleted boolean default false
);

create table if not exists public.project_information
(
    id bigint not null primary key,
    project_id bigint not null
        constraint fk_project
            references public.project(id)
            on delete cascade
            on update cascade,
    link varchar not null,
    description varchar not null
);