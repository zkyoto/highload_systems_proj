package ru.ifmo.cs.interviewers.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;

public record InterviewerActivatedEvent(
        @JsonProperty("interviewerId") InterviewerId interviewerId,
        @JsonProperty("occurredOn") Instant occurredOn
) implements InterviewerEvent {
    public static final String EVENT_TYPE = "InterviewerActivated";

    public static InterviewerActivatedEvent fromActivatedEntity(Interviewer interviewer) {
        return new InterviewerActivatedEvent(interviewer.getId(), interviewer.getUpdatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}
