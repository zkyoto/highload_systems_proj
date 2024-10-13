package ru.itmo.cs.app.interviewing.interview_result.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;

public record InterviewResultCreatedEvent(
        InterviewResultId interviewResultId,
        Instant occurredOn
) implements InterviewResultEvent {
    public static final String EVENT_TYPE = "InterviewResultCreated";

    public static InterviewResultCreatedEvent fromEntity(InterviewResult interviewResult) {
        return new InterviewResultCreatedEvent(interviewResult.getId(), interviewResult.getCreatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

}
