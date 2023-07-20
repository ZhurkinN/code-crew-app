create table public.position_request
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
    is_invite    boolean not null,
    cover_letter varchar(255),
    created_when timestamp    default now(),
    is_deleted   boolean      default false
);