package ru.itmo.cs.app.interviewing.interviewer.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record InterviewerActivatedEvent(InterviewerId interviewerId, Instant occurredOn) implements InterviewerEvent {
    public static InterviewerActivatedEvent fromActivatedEntity(Interviewer interviewer) {
        return new InterviewerActivatedEvent(interviewer.getId(), interviewer.getUpdatedAt());
    }

    @Override
    public String eventType() {
        return "InterviewerActivatedEvent";
    }
}
