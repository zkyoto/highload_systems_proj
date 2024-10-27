package ru.itmo.cs.app.interviewing.interview.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public record InterviewRescheduledEvent(
        @JsonProperty("interviewId") InterviewId interviewId,
        @JsonProperty("occurredOn") Instant occurredOn,
        @JsonProperty("scheduledFor") Instant scheduledFor
) implements InterviewEvent {
    public static final String EVENT_TYPE = "InterviewRescheduledEvent";

    public static InterviewRescheduledEvent fromEntity(Interview interview) {
        return new InterviewRescheduledEvent(interview.getId(), interview.getUpdatedAt(), interview.getScheduledFor());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

}
