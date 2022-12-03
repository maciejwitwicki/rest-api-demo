CREATE TABLE IF NOT EXISTS performance
(
    id      varchar primary key,
    type    varchar not null,
    period  varchar not null,
    rating  numeric not null,
    user_id varchar not null,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_period UNIQUE (period, user_id)
);
