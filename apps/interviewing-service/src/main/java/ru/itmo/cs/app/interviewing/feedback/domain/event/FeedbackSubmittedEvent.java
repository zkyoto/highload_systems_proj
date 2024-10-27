package ru.itmo.cs.app.interviewing.feedback.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

public record FeedbackSubmittedEvent(
        @JsonProperty("feedback_id") FeedbackId feedbackId,
        @JsonProperty("occurred_on") Instant occurredOn
) implements FeedbackEvent {
    public static final String EVENT_TYPE = "FeedbackSubmittedEvent";

    public static FeedbackSubmittedEvent fromEntity(Feedback feedback) {
        return new FeedbackSubmittedEvent(feedback.getId(), feedback.getUpdatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

}
