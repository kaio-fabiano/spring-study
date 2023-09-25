create sequence access_log_id_seq start 1 increment 1;
create sequence hibernate_sequence start 1 increment 1;

create table if not exists users
(
    id         UUID         NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    cpf        VARCHAR(14)  NOT NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    phone      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);