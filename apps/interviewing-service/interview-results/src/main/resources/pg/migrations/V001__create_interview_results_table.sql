create table interview_results
(
    id          uuid      not null,
    created_at  timestamp not null,
    updated_at  timestamp not null,
    feedback_id text      not null,
    verdict     text      not null,

    primary key (id),
    unique (feedback_id)
);
