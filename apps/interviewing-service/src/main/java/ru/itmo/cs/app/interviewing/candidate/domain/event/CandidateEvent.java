package ru.itmo.cs.app.interviewing.candidate.domain.event;

import java.time.Instant;

public interface CandidateEvent {
    String eventType();
    Instant occurredOn();
}
