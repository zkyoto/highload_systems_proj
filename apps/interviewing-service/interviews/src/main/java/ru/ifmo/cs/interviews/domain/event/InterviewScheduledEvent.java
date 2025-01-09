package ru.ifmo.cs.interviews.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.value.InterviewId;

public record InterviewScheduledEvent(
        @JsonProperty("interviewId") InterviewId interviewId,
        @JsonProperty("occurredOn") Instant occurredOn,
        @JsonProperty("interviewerId") String interviewerId,
        @JsonProperty("candidateId") String candidateId,
        @JsonProperty("scheduledFor") Instant scheduledFor
) implements InterviewEvent {
    public static final String EVENT_TYPE = "InterviewScheduledEvent";

    public static InterviewScheduledEvent fromEntity(Interview interview) {
        return new InterviewScheduledEvent(interview.getId(),
                                           interview.getCreatedAt(),
                                           interview.getInterviewerId(),
                                           interview.getCandidateId(),
                                           interview.getScheduledFor());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

}
