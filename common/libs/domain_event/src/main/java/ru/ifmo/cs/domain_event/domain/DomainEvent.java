package ru.ifmo.cs.domain_event.domain;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface DomainEvent {
    @JsonIgnore
    String eventType();
    Instant occurredOn();
}
