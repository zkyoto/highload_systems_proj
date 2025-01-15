package ru.ifmo.cs.interviews.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for canceling an interview")
public record CancelInterviewRequestBodyDto(
        @JsonProperty("interview_id")
        @Schema(description = "UUID of the interview to be canceled", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174003")
        String interviewId
) {}