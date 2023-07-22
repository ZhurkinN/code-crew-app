alter table public.notification
    drop constraint fk_user_notification;
alter table public.notification
    drop constraint fk_request_notification;

drop table public.notification;