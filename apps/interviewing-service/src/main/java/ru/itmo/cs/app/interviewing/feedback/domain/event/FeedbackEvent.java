package ru.itmo.cs.app.interviewing.feedback.domain.event;

import java.time.Instant;

public interface FeedbackEvent {
    String eventType();
    Instant occurredOn();
}
