package ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetAllInterviewersResponseBodyDto(
        @JsonValue List<InterviewerResponseDto> list
) {}
