package ru.ifmo.cs.feedbacks.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;

public record FeedbackCreatedEvent(
        @JsonProperty("feedback_id") FeedbackId feedbackId,
        @JsonProperty("occurred_on") Instant occurredOn
)  implements FeedbackEvent{
    public static final String EVENT_TYPE = "FeedbackCreatedEvent";

    public static FeedbackCreatedEvent fromEntity(Feedback feedback) {
        return new FeedbackCreatedEvent(feedback.getId(), feedback.getCreatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

}
