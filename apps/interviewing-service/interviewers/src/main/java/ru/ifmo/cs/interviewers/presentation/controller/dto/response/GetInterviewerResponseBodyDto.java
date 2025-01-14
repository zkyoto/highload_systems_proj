package ru.ifmo.cs.interviewers.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body containing details of an interviewer")
public record GetInterviewerResponseBodyDto(

        @JsonValue
        @Schema(description = "The interviewer response details")
        InterviewerResponseDto interviewerResponseDto

) {}