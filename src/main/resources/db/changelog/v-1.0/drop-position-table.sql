alter table public.position
    drop constraint fk_project;

alter table public.position
    drop constraint fk_direction;

alter table public.position
    drop constraint fk_user;

drop table public.position;