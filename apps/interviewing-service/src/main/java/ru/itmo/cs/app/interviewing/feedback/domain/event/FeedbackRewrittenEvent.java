package ru.itmo.cs.app.interviewing.feedback.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

public record FeedbackRewrittenEvent(FeedbackId feedbackId, Instant occurredOn) implements FeedbackEvent {
    public static final String EVENT_TYPE = "FeedbackRewrittenEvent";

    public static FeedbackRewrittenEvent fromEntity(Feedback feedback) {
        return new FeedbackRewrittenEvent(feedback.getId(), feedback.getUpdatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}
