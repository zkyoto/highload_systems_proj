package ru.itmo.cs.app.interviewing.interviewer.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record InterviewerCreatedEvent(
        @JsonProperty("interviewId") InterviewerId interviewerId,
        @JsonProperty("occurredOn") Instant occurredOn
) implements InterviewerEvent {
    public static final String EVENT_TYPE = "InterviewerCreatedEvent";

    public static InterviewerCreatedEvent fromEntity(Interviewer interviewer) {
        return new InterviewerCreatedEvent(interviewer.getId(), interviewer.getCreatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}
