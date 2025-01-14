package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Ответ с ожидающими отзывами")
public record FeedbacksPendingResultResponseBodyDto(
        @Schema(description = "Список DTO ожидающих отзывов",
                example = "[{\"feedback_id\":\"123e4567-e89b-12d3-a456-426614174000\",\"interview_id\":\"987e6543-e21b-12d3-a456-426614174001\",\"grade\":5,\"comment\":\"Ожидается подтверждение от менеджера.\"}]")
        List<FeedbackPendingResultDto> feedbackPendingResultDtoList
) {}