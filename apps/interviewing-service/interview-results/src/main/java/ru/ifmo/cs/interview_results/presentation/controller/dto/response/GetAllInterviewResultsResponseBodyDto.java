package ru.ifmo.cs.interview_results.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.InterviewResultResponseDto;

public record GetAllInterviewResultsResponseBodyDto(
        @JsonValue List<InterviewResultResponseDto> list
) {}
