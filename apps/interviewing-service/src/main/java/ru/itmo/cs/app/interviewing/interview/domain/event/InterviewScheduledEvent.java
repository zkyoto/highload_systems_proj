package ru.itmo.cs.app.interviewing.interview.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record InterviewScheduledEvent(
        @JsonProperty("interviewId") InterviewId interviewId,
        @JsonProperty("occurredOn") Instant occurredOn,
        @JsonProperty("interviewerId") InterviewerId interviewerId,
        @JsonProperty("candidateId") CandidateId candidateId,
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
