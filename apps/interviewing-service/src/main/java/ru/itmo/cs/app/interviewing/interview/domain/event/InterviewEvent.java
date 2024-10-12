package ru.itmo.cs.app.interviewing.interview.domain.event;

import java.time.Instant;

public interface InterviewEvent {
    String eventType();
    Instant occurredOn();
}
