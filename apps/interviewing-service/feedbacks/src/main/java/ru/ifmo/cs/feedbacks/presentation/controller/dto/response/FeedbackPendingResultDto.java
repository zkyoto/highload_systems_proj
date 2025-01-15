package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.Grade;

@Schema(description = "Модель данных для отображения ожидающего одобрения отзыва")
public record FeedbackPendingResultDto(
        @JsonProperty("feedback_id")
        @Schema(description = "Уникальный идентификатор отзыва", example = "123e4567-e89b-12d3-a456-426614174000")
        FeedbackId feedbackId,

        @JsonProperty("interview_id")
        @Schema(description = "Уникальный идентификатор собеседования, связанного с отзывом", example = "987e6543-e21b-12d3-a456-426614174001")
        String interviewId,

        @JsonProperty("grade")
        @Schema(description = "Оценка, дана в отзыве", example = "5")
        Grade grade,

        @JsonProperty("comment")
        @Schema(description = "Комментарий к отзыву", example = "Отзыв по собеседованию в IT направлении был положительным.")
        Comment comment
) {
    public static FeedbackPendingResultDto from(Feedback feedback) {
        return new FeedbackPendingResultDto(feedback.getId(),
                feedback.getInterviewId(),
                feedback.getGrade(),
                feedback.getComment());
    }
}