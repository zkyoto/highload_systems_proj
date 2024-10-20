create table interviewers
(
    id         uuid      not null primary key,
    user_id    bigint    not null,
    full_name  text      not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    status     text      not null
);