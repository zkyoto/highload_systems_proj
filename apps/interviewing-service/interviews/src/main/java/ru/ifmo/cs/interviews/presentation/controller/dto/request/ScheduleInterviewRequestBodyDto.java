package ru.ifmo.cs.interviews.presentation.controller.dto.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for scheduling an interview")
public record ScheduleInterviewRequestBodyDto(
        @JsonProperty("interviewer_id")
        @Schema(description = "UUID of the interviewer", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174000")
        String interviewerId,

        @JsonProperty("candidate_id")
        @Schema(description = "UUID of the candidate", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174001")
        String candidateId,

        @JsonProperty("scheduled_time")
        @Schema(description = "Scheduled time for the interview", example = "2023-04-05T10:15:30Z")
        Instant scheduledTime
) {}