package ru.itmo.cs.app.interviewing.interviewer.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record InterviewerDemotedEvent(InterviewerId interviewerId, Instant occurredOn) implements InterviewerEvent {
    public static InterviewerDemotedEvent fromDemotedEntity(Interviewer interviewer) {
        return new InterviewerDemotedEvent(interviewer.getId(), interviewer.getUpdatedAt());
    }

    @Override
    public String eventType() {
        return "InterviewerDemotedEvent";
    }
}
