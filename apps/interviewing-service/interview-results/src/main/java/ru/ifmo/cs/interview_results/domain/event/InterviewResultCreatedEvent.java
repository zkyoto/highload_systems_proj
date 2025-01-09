package ru.ifmo.cs.interview_results.domain.event;

import java.time.Instant;

import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.value.InterviewResultId;

public record InterviewResultCreatedEvent(
        InterviewResultId interviewResultId,
        Instant occurredOn,
        String feedbackId
) implements InterviewResultEvent {
    public static final String EVENT_TYPE = "InterviewResultCreated";

    public static InterviewResultCreatedEvent fromEntity(InterviewResult interviewResult) {
        return new InterviewResultCreatedEvent(
                interviewResult.getId(),
                interviewResult.getCreatedAt(),
                interviewResult.getFeedbackId()
        );
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

}
