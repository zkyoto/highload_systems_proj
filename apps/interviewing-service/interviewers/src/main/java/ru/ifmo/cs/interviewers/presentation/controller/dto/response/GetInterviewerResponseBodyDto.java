package ru.ifmo.cs.interviewers.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetInterviewerResponseBodyDto(
        @JsonValue InterviewerResponseDto interviewerResponseDto
) {}
