package ru.itmo.cs.app.interviewing.feedback.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

public record FeedbackCreatedEvent(FeedbackId feedbackId, Instant occurredOn)  implements FeedbackEvent{

    public static FeedbackCreatedEvent fromEntity(Feedback feedback) {
        return new FeedbackCreatedEvent(feedback.getId(), feedback.getCreatedAt());
    }

    @Override
    public String eventType() {
        return "FeedbackCreatedEvent";
    }

}
