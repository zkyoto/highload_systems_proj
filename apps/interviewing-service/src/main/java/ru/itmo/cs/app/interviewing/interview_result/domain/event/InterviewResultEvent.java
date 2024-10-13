package ru.itmo.cs.app.interviewing.interview_result.domain.event;

import java.time.Instant;

public interface InterviewResultEvent {
    String eventType();
    Instant occurredOn();
}
