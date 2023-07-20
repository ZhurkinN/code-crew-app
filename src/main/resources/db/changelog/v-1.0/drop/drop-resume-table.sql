alter table public.resume
    drop constraint fk_direction;

alter table public.resume
    drop constraint fk_user_id;

drop table public.resume;