create table r2dbc_passport_user_entity
(
    id         uuid      not null,
    user_id    bigint    not null,
    full_name  text      not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    roles      text[]    not null,

    primary key (id),
    unique (user_id)
);

