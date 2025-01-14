package ru.ifmo.cs.interviews.presentation.controller.dto.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for rescheduling an interview")
public record RescheduleInterviewRequestBodyDto(
        @JsonProperty("interview_id")
        @Schema(description = "UUID of the interview to be rescheduled", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174002")
        String interviewId,

        @JsonProperty("new_scheduled_time")
        @Schema(description = "New scheduled time for the interview", example = "2023-04-06T14:00:00Z")
        Instant newScheduledTime
) {}