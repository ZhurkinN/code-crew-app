create table public.notification
(
    id           bigint       not null primary key generated always as identity,
    type         varchar(255) not null
        constraint fk_notification_type
            references public.dictionary_notification_type (type_name)
            on delete set default
            on update cascade,
    user_id      bigint
        constraint fk_user_notification
            references public.users (id)
            on delete set null
            on update cascade,
    request_id   bigint
        constraint fk_request_notification
            references public.position_request (id)
            on delete set null
            on update cascade,
    created_when timestamp default now()
);