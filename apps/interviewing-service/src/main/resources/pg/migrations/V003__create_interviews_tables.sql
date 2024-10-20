create table interviews
(
    id             uuid      not null primary key,
    created_at     timestamp not null,
    updated_at     timestamp not null,
    interviewer_id uuid      not null,
    candidate_id   uuid      not null
);

create table schedules
(
    id            uuid      not null primary key,
    created_at    timestamp not null,
    updated_at    timestamp not null,
    scheduled_for timestamp not null,
    status        text      not null,
    interview_id  uuid      not null
);

ALTER TABLE interviews
    ADD CONSTRAINT fk_candidate_id
        FOREIGN KEY (candidate_id) REFERENCES candidates (id);

ALTER TABLE interviews
    ADD CONSTRAINT fk_interviewer_id
        FOREIGN KEY (interviewer_id) REFERENCES interviewers (id);

ALTER TABLE schedules
    ADD CONSTRAINT fk_interview_id
        FOREIGN KEY (interview_id) REFERENCES interviews (id);