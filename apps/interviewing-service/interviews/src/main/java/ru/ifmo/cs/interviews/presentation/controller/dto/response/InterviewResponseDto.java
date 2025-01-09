package ru.ifmo.cs.interviews.presentation.controller.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.value.InterviewId;

public record InterviewResponseDto(
        @JsonProperty("interview_id") InterviewId id,
        @JsonProperty("interviewer_id") String interviewerId,
        @JsonProperty("candidate_id") String candidateId,
        @JsonProperty("scheduled_for") @Nullable Instant scheduledFor
) {
    public static InterviewResponseDto from(Interview interview) {
        return new InterviewResponseDto(
                interview.getId(),
                interview.getInterviewerId(),
                interview.getCandidateId(),
                interview.getScheduledForO().orElse(null)
        );
    }
}
