create table interviews
(
    id             uuid      not null,
    created_at     timestamp not null,
    updated_at     timestamp not null,
    interviewer_id text      not null,
    candidate_id   text      not null,

    primary key (id)
);

create table schedules
(
    id            uuid      not null,
    created_at    timestamp not null,
    updated_at    timestamp not null,
    scheduled_for timestamp not null,
    status        text      not null,
    interview_id  uuid      not null,

    primary key (id)
);

ALTER TABLE schedules
    ADD CONSTRAINT fk_interview_id
        FOREIGN KEY (interview_id) REFERENCES interviews (id);