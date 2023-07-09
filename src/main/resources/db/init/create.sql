create table if not exists public.dictionary_direction
(
    direction_name varchar(255) not null
        primary key,
    description    varchar(255) not null
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
    id               bigint              not null
        primary key generated always as identity,
    email            varchar(255) unique not null,
    name             varchar(255)        not null,
    surname          varchar(255)        not null,
    password         varchar(255)        not null,
    picture_link     varchar(255),
    main_information varchar(255),
    contacts         varchar[] default '{}'::varchar[],
    created_when     timestamp default now(),
    is_deleted       boolean   default false
);

create table if not exists public.resume
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
    skills       varchar[] default '{}'::varchar[],

    unique (user_id, direction)
);

create table if not exists public.project
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

create table public.position
(
    id           bigint       not null primary key generated always as identity,
    is_visible   boolean   default true,
    project_id   bigint       not null
        constraint fk_project
            references public.project (id)
            on delete cascade
            on update cascade,
    direction    varchar(255) not null
        constraint fk_direction
            references public.dictionary_direction (direction_name)
            on delete set null
            on update cascade,
    user_id      bigint    default null
        constraint fk_user
            references public.users (id)
            on delete set default
            on update cascade,
    description  varchar(255) not null,
    skills       varchar[] default '{}'::varchar[],
    join_date    timestamp default null,
    created_when timestamp default now(),
    is_deleted   boolean   default false
);


create table if not exists public.position_request
(
    id           bigint  not null primary key generated always as identity,
    resume_id    bigint  not null
        constraint fk_resume
            references public.resume (id)
            on delete cascade
            on update cascade,
    position_id  bigint  not null
        constraint fk_positions
            references public.position (id)
            on delete cascade
            on update cascade,
    status       varchar(255) default 'IN_CONSIDERATION'
        constraint fk_request_status
            references public.dictionary_request_status (status_name)
            on delete set default
            on update cascade,
    cover_letter varchar(255),
    is_invite    boolean not null,
    created_when timestamp    default now(),
    is_deleted   boolean      default false

);

create table if not exists public.project_information
(
    id          bigint  not null primary key generated always as identity,
    project_id  bigint  not null
        constraint fk_project
            references public.project (id)
            on delete cascade
            on update cascade,
    link        varchar not null,
    description varchar not null
);

create table if not exists public.project_members
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
);
