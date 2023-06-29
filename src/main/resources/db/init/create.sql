DROP TABLE IF EXISTS tbl_users;

CREATE TABLE tbl_users (
                       id   BIGINT NOT NULL UNIQUE PRIMARY KEY,
                       name  VARCHAR(255) NOT NULL UNIQUE
);