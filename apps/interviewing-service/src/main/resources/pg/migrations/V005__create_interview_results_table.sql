create table interview_results
(
    id          uuid      not null,
    created_at  timestamp not null,
    updated_at  timestamp not null,
    feedback_id uuid      not null,
    verdict     text      not null,

    primary key (id),
    unique (feedback_id)
);

ALTER TABLE interview_results
    ADD CONSTRAINT fk_feedback_id
        FOREIGN KEY (feedback_id) REFERENCES feedbacks (id);