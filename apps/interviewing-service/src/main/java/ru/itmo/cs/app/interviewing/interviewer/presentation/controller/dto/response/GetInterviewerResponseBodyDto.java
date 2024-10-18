package ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetInterviewerResponseBodyDto(
        @JsonValue InterviewerResponseDto interviewerResponseDto
) {}
