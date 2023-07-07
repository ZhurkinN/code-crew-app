alter table public.project
    drop constraint fk_leader_id;

alter table public.project
    drop constraint fk_project_status;

drop table public.project;