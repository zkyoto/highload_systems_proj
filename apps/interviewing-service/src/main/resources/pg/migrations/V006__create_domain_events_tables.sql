create table domain_events
(
    id                    uuid      not null primary key,
    created_at            timestamp not null,
    processed_at          timestamp,
    type                  text      not null,
    status                text      not null,
    payload               text      not null,
    retry_counter         integer   not null,
    event_sequence_number bigserial
);

create table domain_event_consumption_logs
(
    event_id                uuid      not null,
    subscriber_reference_id text      not null,
    consumed_at             timestamp not null,

    primary key (event_id, subscriber_reference_id)
);
