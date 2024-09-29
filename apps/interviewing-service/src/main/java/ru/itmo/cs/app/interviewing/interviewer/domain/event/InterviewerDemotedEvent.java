package ru.itmo.cs.app.interviewing.interviewer.domain.event;

import java.time.Instant;

import lombok.Value;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

@Value
public class InterviewerDemotedEvent implements InterviewerEvent{
    InterviewerId interviewerId;
    Instant occurredOn;

    public static InterviewerDemotedEvent fromDemotedEntity(Interviewer interviewer) {
        return new InterviewerDemotedEvent(interviewer.getInterviewerId(), interviewer.getCreatedAt());
    }
}
