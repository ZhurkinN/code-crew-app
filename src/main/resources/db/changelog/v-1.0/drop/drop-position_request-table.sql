alter table public.position_request
    drop constraint fk_resume;

alter table public.position_request
    drop constraint fk_positions;

alter table public.position_request
    drop constraint fk_request_status;

drop table public.position_request;