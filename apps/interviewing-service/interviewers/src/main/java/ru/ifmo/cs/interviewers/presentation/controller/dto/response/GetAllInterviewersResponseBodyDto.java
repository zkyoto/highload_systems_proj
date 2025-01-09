package ru.ifmo.cs.interviewers.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import ru.ifmo.cs.interviewers.presentation.controller.dto.response.InterviewerResponseDto;

public record GetAllInterviewersResponseBodyDto(
        @JsonValue List<InterviewerResponseDto> list
) {}
