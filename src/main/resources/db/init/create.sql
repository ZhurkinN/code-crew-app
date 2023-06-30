create sequence public.contact_information_seq
    increment by 50;

alter sequence public.contact_information_seq owner to postgres;

create sequence public.direction_seq
    increment by 50;

alter sequence public.direction_seq owner to postgres;

create sequence public.participants_seq
    increment by 50;

alter sequence public.participants_seq owner to postgres;

create sequence public.resume_seq
    increment by 50;

alter sequence public.resume_seq owner to postgres;

create sequence public.skills_seq
    increment by 50;

alter sequence public.skills_seq owner to postgres;

create sequence public.team_request_seq
    increment by 50;

alter sequence public.team_request_seq owner to postgres;

create sequence public.team_seq
    increment by 50;

alter sequence public.team_seq owner to postgres;

create sequence public.team_status_seq
    increment by 50;

alter sequence public.team_status_seq owner to postgres;

create sequence public.users_seq
    increment by 50;

alter sequence public.users_seq owner to postgres;



create table if not exists public.direction
(
    id             bigint not null
        primary key,
    direction_name varchar(255)
);

alter table public.direction
    owner to postgres;

create table if not exists public.skills
(
    id         bigint not null
        primary key,
    skill_name varchar(255)
);

alter table public.skills
    owner to postgres;

create table if not exists public.team_status
(
    id          bigint not null
        primary key,
    status_name varchar(255)
);

alter table public.team_status
    owner to postgres;

create table if not exists public.users
(
    id           bigint not null
        primary key,
    email        varchar(255),
    full_name    varchar(255),
    login        varchar(255),
    password     varchar(255),
    picture_link varchar(255)
);

alter table public.users
    owner to postgres;

create table if not exists public.contact_information
(
    id           bigint not null
        primary key,
    link         varchar(255),
    social_media varchar(255),
    user_id      bigint
        constraint fk_user_id
            references public.users
);

alter table public.contact_information
    owner to postgres;

create table if not exists public.resume
(
    id           bigint not null
        primary key,
    description  varchar(255),
    is_active    boolean,
    direction_id bigint
        constraint fk_direction_id
            references public.direction,
    user_id      bigint
        constraint fk_user_id
            references public.users
);

alter table public.resume
    owner to postgres;

create table if not exists public.resume_skills
(
    skill_id  bigint not null
        constraint fk_skills_resume
            references public.skills,
    resume_id bigint not null
        constraint fk_resume_skills
            references public.resume,
    primary key (skill_id, resume_id)
);

alter table public.resume_skills
    owner to postgres;

create table if not exists public.team
(
    id                bigint not null
        primary key,
    description       varchar(255),
    is_visible        boolean,
    programmers_count integer,
    theme             varchar(255),
    status_id         bigint
        constraint fk_team_status_id
            references public.team_status,
    leader_id         bigint
        constraint fk_user_id
            references public.users
);

alter table public.team
    owner to postgres;

create table if not exists public.participants
(
    id           bigint not null
        primary key,
    description  varchar(255),
    direction_id bigint
        constraint fk_direction_id
            references public.direction,
    team_id      bigint
        constraint fk_team_id
            references public.team,
    user_id      bigint
        constraint fk_user_id
            references public.users
);

alter table public.participants
    owner to postgres;

create table if not exists public.participant_skills
(
    skill_id       bigint not null
        constraint fk_skills_participants
            references public.skills,
    participant_id bigint not null
        constraint fk_participants_skill
            references public.participants,
    primary key (skill_id, participant_id)
);

alter table public.participant_skills
    owner to postgres;

create table if not exists public.team_request
(
    id          bigint not null
        primary key,
    is_accepted boolean,
    resume_id   bigint not null
        constraint fk_resume_id
            references public.resume,
    user_id     bigint default null
        constraint fk_user_id
            references public.users,
    vacancy_id  bigint not null
        constraint fk_vacancy_id
            references public.participants
);

alter table public.team_request
    owner to postgres;

