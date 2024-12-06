create table request_log
(
    id      serial not null,
    method  text   not null,
    url     text   not null,
    headers text   not null,
    trace_id uuid   not null,

    primary key (id),
    unique (trace_id)
);

create table response_log
(
    id         serial not null,
    status_code int    not null,
    headers    text   not null,
    trace_id    uuid   not null,

    primary key (id),
    unique (trace_id)
);
