alter table public.project_members
    drop constraint fk_users;

alter table public.project_members
    drop constraint fk_projects;

drop table public.project_members
