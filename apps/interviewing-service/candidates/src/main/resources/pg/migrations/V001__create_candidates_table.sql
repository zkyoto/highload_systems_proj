create table candidates
(
    id         uuid      not null,
    full_name  text      not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    status     text      not null,

    primary key (id)
);