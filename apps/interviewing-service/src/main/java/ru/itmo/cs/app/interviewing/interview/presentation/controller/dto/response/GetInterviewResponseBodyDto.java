package ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetInterviewResponseBodyDto(
        @JsonValue InterviewResponseDto interviewResponseDto
) {}
