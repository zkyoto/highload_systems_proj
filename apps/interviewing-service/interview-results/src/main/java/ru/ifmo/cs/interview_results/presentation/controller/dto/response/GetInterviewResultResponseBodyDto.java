package ru.ifmo.cs.interview_results.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ тела для получения результата собеседования")
public record GetInterviewResultResponseBodyDto(
        @JsonValue
        InterviewResultResponseDto interviewResultResponseDto
) {}