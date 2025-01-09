create table feedbacks
(
    id           uuid      not null,
    created_at   timestamp not null,
    updated_at   timestamp not null,
    interview_id text      not null,
    status       text      not null,
    grade        int,
    comment      text,

    primary key (id),
    unique (interview_id)
);
