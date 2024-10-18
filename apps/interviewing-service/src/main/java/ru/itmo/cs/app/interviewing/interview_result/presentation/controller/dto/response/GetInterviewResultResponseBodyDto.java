package ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetInterviewResultResponseBodyDto(
        @JsonValue InterviewResultResponseDto interviewResultResponseDto
) {}
