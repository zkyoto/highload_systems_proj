package ru.itmo.cs.app.interviewing.interviewer.domain.event;

import java.time.Instant;

import lombok.Value;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

@Value
public class InterviewerActivatedEvent implements InterviewerEvent{
    InterviewerId interviewerId;
    Instant occurredOn;

    public static InterviewerActivatedEvent fromActivatedEntity(Interviewer interviewer) {
        return new InterviewerActivatedEvent(interviewer.getInterviewerId(), interviewer.getCreatedAt());
    }
}
