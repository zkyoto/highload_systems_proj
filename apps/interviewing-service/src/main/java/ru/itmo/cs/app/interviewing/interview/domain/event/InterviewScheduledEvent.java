package ru.itmo.cs.app.interviewing.interview.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record InterviewScheduledEvent(
        InterviewId interviewId,
        Instant occurredOn,
        InterviewerId interviewerId,
        CandidateId candidateId,
        Instant scheduledFor
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
