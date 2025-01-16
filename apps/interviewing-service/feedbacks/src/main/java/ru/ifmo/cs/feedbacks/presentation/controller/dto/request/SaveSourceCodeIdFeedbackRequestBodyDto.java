package ru.ifmo.cs.feedbacks.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для сохранения комментария к отзыву")
public record SaveSourceCodeIdFeedbackRequestBodyDto(
        @JsonProperty("feedback_id")
        @Schema(description = "Уникальный идентификатор отзыва", example = "123e4567-e89b-12d3-a456-426614174000")
        String feedbackId,

        @JsonProperty("source_code_id")
        @Schema(description = "Id файла в file-manager", example = "123")
        String sourceCodeId
) {}