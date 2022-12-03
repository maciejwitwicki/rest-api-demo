CREATE TABLE IF NOT EXISTS users
(
    id       varchar primary key,
    name     varchar   not null,
    job      varchar   not null,
    salary   numeric default 0,
    hired_on timestamp not null
);

CREATE TABLE IF NOT EXISTS cars
(
    id           varchar primary key,
    registration varchar unique not null,
    model        varchar not null,
    user_id      varchar,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);