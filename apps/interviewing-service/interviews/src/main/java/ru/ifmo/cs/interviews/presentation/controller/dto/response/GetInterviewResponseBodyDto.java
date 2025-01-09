package ru.ifmo.cs.interviews.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetInterviewResponseBodyDto(
        @JsonValue InterviewResponseDto interviewResponseDto
) {}
