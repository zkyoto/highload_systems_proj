package ru.ifmo.cs.interviews.domain.event;


import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.value.InterviewId;

public record InterviewCancelledEvent(
        @JsonProperty("interviewId") InterviewId interviewId,
        @JsonProperty("occurredOn") Instant occurredOn,
        @JsonProperty("interviewerId") String interviewerId,
        @JsonProperty("candidateId") String candidateId
) implements InterviewEvent {
    public static final String EVENT_TYPE = "InterviewCancelledEvent";

    public static InterviewCancelledEvent fromEntity(Interview interview) {
        return new InterviewCancelledEvent(
                interview.getId(),
                Instant.now(),
                interview.getInterviewerId(),
                interview.getCandidateId()
        );
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}
