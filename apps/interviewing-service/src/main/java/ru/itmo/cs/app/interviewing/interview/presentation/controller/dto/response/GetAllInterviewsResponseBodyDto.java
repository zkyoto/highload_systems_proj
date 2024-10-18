package ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetAllInterviewsResponseBodyDto(
        @JsonValue List<InterviewResponseDto> list
) {}
