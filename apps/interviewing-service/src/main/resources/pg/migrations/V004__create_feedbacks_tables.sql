create table feedbacks
(
    id           uuid      not null,
    created_at   timestamp not null,
    updated_at   timestamp not null,
    interview_id uuid      not null,
    status       text      not null,
    grade        int,
    comment      text,

    primary key (id),
    unique (interview_id)
);

ALTER TABLE feedbacks
    ADD CONSTRAINT fk_interview_id
        FOREIGN KEY (interview_id) REFERENCES interviews (id);