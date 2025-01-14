package ru.ifmo.cs.interviews.presentation.controller.dto.response;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.value.InterviewId;

@Schema(description = "DTO representing an interview")
public record InterviewResponseDto(
        @JsonProperty("interview_id")
        @Schema(description = "UUID of the interview", example = "123e4567-e89b-12d3-a456-426614174000")
        InterviewId id,

        @JsonProperty("interviewer_id")
        @Schema(description = "UUID of the interviewer", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174001")
        String interviewerId,

        @JsonProperty("candidate_id")
        @Schema(description = "UUID of the candidate", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174002")
        String candidateId,

        @JsonProperty("scheduled_for")
        @Nullable
        @Schema(description = "Scheduled time for the interview or null if not scheduled", example = "2023-04-05T10:15:30Z")
        Instant scheduledFor
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