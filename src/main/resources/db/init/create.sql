create sequence if not exists public.contact_information_seq
    increment by 50;

create sequence if not exists public.direction_seq
    increment by 50;

create sequence if not exists public.participants_seq
    increment by 50;

create sequence if not exists public.resume_seq
    increment by 50;

create sequence if not exists public.skills_seq
    increment by 50;

create sequence if not exists public.team_request_seq
    increment by 50;

create sequence if not exists public.team_seq
    increment by 50;

create sequence if not exists public.team_status_seq
    increment by 50;

create sequence if not exists public.users_seq
    increment by 50;



create table if not exists public.direction
(
    id             bigint not null
        primary key,
    direction_name varchar(255) not null
);

create table if not exists public.skills
(
    id         bigint not null
        primary key,
    skill_name varchar(255) not null
);

create table if not exists public.team_status
(
    id          bigint not null
        primary key,
    status_name varchar(255) not null
);

create table if not exists public.users
(
    id           bigint not null
        primary key,
    email        varchar(255),
    full_name    varchar(255) not null,
    login        varchar(255) not null,
    password     varchar(255) not null,
    picture_link varchar(255)
);

create table if not exists public.contact_information
(
    id           bigint not null
        primary key,
    link         varchar(255) not null,
    social_media varchar(255) not null,
    user_id      bigint not null
        constraint fk_user_id
            references public.users
);

create table if not exists public.resume
(
    id           bigint not null
        primary key,
    description  varchar(255),
    is_active    boolean default true,
    direction_id bigint not null
        constraint fk_direction_id
            references public.direction,
    user_id      bigint not null
        constraint fk_user_id
            references public.users
);

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

create table if not exists public.team
(
    id                bigint not null
        primary key,
    description       varchar(255),
    is_visible        boolean default true,
    programmers_count integer not null,
    theme             varchar(255) not null,
    status_id         bigint not null
        constraint fk_team_status_id
            references public.team_status,
    leader_id         bigint
        constraint fk_user_id
            references public.users
);

create table if not exists public.participants
(
    id           bigint not null
        primary key,
    description  varchar(255),
    direction_id bigint not null
        constraint fk_direction_id
            references public.direction,
    team_id      bigint not null
        constraint fk_team_id
            references public.team,
    user_id      bigint default null
        constraint fk_user_id
            references public.users
);

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

create table if not exists public.team_request
(
    id          bigint not null
        primary key,
    is_accepted boolean,
    resume_id   bigint not null
        constraint fk_resume_id
            references public.resume,
    user_id     bigint not null
        constraint fk_user_id
            references public.users,
    vacancy_id  bigint not null
        constraint fk_vacancy_id
            references public.participants
);
