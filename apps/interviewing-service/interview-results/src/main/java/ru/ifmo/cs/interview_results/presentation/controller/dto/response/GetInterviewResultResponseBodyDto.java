package ru.ifmo.cs.interview_results.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.InterviewResultResponseDto;

public record GetInterviewResultResponseBodyDto(
        @JsonValue InterviewResultResponseDto interviewResultResponseDto
) {}
