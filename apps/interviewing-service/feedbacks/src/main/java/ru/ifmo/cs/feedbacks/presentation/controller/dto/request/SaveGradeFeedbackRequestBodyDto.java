package ru.ifmo.cs.feedbacks.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для сохранения оценки в отзыве")
public record SaveGradeFeedbackRequestBodyDto(
        @JsonProperty("feedback_id")
        @Schema(description = "Уникальный идентификатор отзыва", example = "123e4567-e89b-12d3-a456-426614174000")
        String feedbackId,

        @JsonProperty("grade")
        @Schema(description = "Оценка, данная в отзыве", example = "8", minimum = "1", maximum = "10")
        Integer grade
) {}