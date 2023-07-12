create table REFRESH_TOKEN_ENTITY
(
    ID            bigint primary key auto_increment,
    REFRESH_TOKEN varchar(21844) not null,
    USERNAME      varchar(255)   not null,
    DATE_CREATE   timestamp default now(),
    REVOKED       boolean
)