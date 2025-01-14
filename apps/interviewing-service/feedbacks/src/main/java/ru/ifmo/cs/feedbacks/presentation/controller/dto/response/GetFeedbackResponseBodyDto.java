package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ, содержащий данные одного конкретного отзыва")
public record GetFeedbackResponseBodyDto(
        @JsonValue
        @Schema(description = "DTO, представляющий информацию об отзыве")
        FeedbackResponseDto feedbackResponseDto
) {}