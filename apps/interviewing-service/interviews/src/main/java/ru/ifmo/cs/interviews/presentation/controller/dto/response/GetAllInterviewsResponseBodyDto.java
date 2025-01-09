package ru.ifmo.cs.interviews.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetAllInterviewsResponseBodyDto(
        @JsonValue List<InterviewResponseDto> list
) {}
