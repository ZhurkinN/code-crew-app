create sequence if not exists public.participants_seq
    increment by 50;

create sequence if not exists public.resume_seq
    increment by 50;

create sequence if not exists public.team_request_seq
    increment by 50;

create sequence if not exists public.team_seq
    increment by 50;

create sequence if not exists public.users_seq
    increment by 50;



create table if not exists public.direction
(
    direction_name varchar(255) not null
        primary key,
    description varchar(255) not null
);

create table if not exists public.project_status
(
    status_name varchar(255) not null
        primary key,
    description varchar(255) not null
);

create table if not exists public.request_status
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
    contacts varchar[] not null
);

create table if not exists public.resume
(
    id           bigint not null
        primary key,
    description  varchar(255),
    is_active    boolean default true,
    direction varchar(255) not null
        constraint fk_direction
            references public.direction(direction_name),
    user_id      bigint not null
        constraint fk_user_id
            references public.users,
    skills varchar[] not null,

    unique (user_id, direction)
);

create table if not exists project
(
    id bigint not null primary key,
    leader_id bigint not null
        constraint fk_leader_id
            references public.users(id),
    title varchar(255) not null,
    theme varchar(255) not null,
    description varchar(255),
    is_visible boolean default true,
    status varchar(255) not null
        constraint fk_project_status
            references public.project_status(status_name)
);

create table positions
(
    id bigint not null primary key,
    project_id bigint not null
        constraint fk_project
            references public.project(id),
    direction varchar(255) not null
        constraint fk_direction
            references public.direction(direction_name),
    user_id bigint default null
        constraint fk_user
            references public.users(id),
    description varchar(255) not null,
    skills varchar[] not null
);


create table if not exists position_request
(
    id bigint not null primary key,
    resume_id bigint not null
        constraint fk_resume
            references public.resume(id),
    position_id bigint not null
        constraint fk_positions
            references public.positions(id),
    status varchar(255)
        constraint fk_request_status
            references public.request_status(status_name),
    cover_letter varchar(255) not null
);
