package ru.itmo.cs.app.interviewing.interviewer.domain.event;

import java.time.Instant;

public interface InterviewerEvent {
    String eventType();
    Instant occurredOn();
}
