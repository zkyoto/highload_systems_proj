package ru.ifmo.cs.interview_results.presentation.controller.dto.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ, содержащий список результатов собеседования")
public record GetAllInterviewResultsResponseBodyDto(
        @JsonValue
        @Schema(description = "Список результатов собеседования", implementation = InterviewResultResponseDto.class)
        List<InterviewResultResponseDto> list
) {}