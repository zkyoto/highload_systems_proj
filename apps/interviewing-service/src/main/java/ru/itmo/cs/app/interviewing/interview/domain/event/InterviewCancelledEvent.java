package ru.itmo.cs.app.interviewing.interview.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public record InterviewCancelledEvent(InterviewId interviewId, Instant occurredOn) implements InterviewEvent {

    public static InterviewCancelledEvent fromEntity(Interview interview) {
        return new InterviewCancelledEvent(interview.getId(), Instant.now());
    }

    @Override
    public String eventType() {
        return "InterviewCancelledEvent";
    }
}
