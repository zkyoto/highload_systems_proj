package ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record InterviewResponseDto(
        @JsonProperty("interview_id") InterviewId id,
        @JsonProperty("interviewer_id") InterviewerId interviewerId,
        @JsonProperty("candidate_id") CandidateId candidateId,
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
