package ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetAllInterviewResultsResponseBodyDto(
        @JsonValue List<InterviewResultResponseDto> list
) {}
