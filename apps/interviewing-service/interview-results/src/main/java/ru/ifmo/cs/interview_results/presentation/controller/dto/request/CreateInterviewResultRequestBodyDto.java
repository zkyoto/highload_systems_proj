package ru.ifmo.cs.interview_results.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.ifmo.cs.interview_results.domain.value.Verdict;

@Schema(description = "Запрос на создание результата собеседования")
public record CreateInterviewResultRequestBodyDto(
        @JsonProperty("feedback_id")
        @Schema(description = "Уникальный идентификатор отзыва", example = "fdbk1234")
        String feedbackId,

        @JsonProperty("verdict")
        @Schema(description = "Вердикт собеседования", example = "APPROVED")
        Verdict verdict
) {}