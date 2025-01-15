package ru.ifmo.cs.interviews.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing details of a single interview")
public record GetInterviewResponseBodyDto(
        @JsonValue
        @Schema(description = "Interview response DTO containing the details of the interview")
        InterviewResponseDto interviewResponseDto
) {}