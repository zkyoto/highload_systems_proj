package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackStatus;
import ru.ifmo.cs.feedbacks.domain.value.Grade;
import ru.ifmo.cs.feedbacks.domain.value.SourceCodeFileId;

@Schema(description = "DTO, представляющий подробную информацию об отзыве")
public record FeedbackResponseDto(
        @JsonProperty("feedback_id")
        @Schema(description = "Уникальный идентификатор отзыва", example = "123e4567-e89b-12d3-a456-426614174000")
        FeedbackId id,

        @JsonProperty("interview_id")
        @Schema(description = "Уникальный идентификатор собеседования, связанного с отзывом", example = "987e6543" +
                "-e21b-12d3-a456-426614174001")
        String interviewId,

        @JsonProperty("status")
        @Schema(description = "Статус отзыва", example = "waiting_for_submit", allowableValues = {"waiting_for_submit"
                , "submitted"})
        FeedbackStatus status,

        @JsonProperty("grade")
        @Schema(description = "Оценка, присвоенная в отзыве", example = "5",
                allowableValues = {"1", "2", "3", "4", "5"}, nullable = true)
        @Nullable Grade grade,

        @JsonProperty("comment")
        @Schema(description = "Комментарий к отзыву", example = "Ожидается подтверждение от менеджера.", nullable =
                true)
        @Nullable Comment comment,

        @JsonProperty("source_code_file_id")
        @Schema(description = "Id файла с исходным кодом кандидата, загруженного в file-manager",
                example = "987e6543-e21b-12d3-a456-426614174001")
        SourceCodeFileId sourceCodeFileId
) {
    public static FeedbackResponseDto from(Feedback feedback) {
        return new FeedbackResponseDto(feedback.getId(),
                feedback.getInterviewId(),
                feedback.getStatus(),
                feedback.getGrade(),
                feedback.getComment(),
                feedback.getSourceCodeFileId());
    }
}