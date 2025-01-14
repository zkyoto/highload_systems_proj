package ru.ifmo.cs.interview_results.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.value.InterviewResultId;
import ru.ifmo.cs.interview_results.domain.value.Verdict;

@Schema(description = "Ответ, содержащий информацию о результате собеседования")
public record InterviewResultResponseDto(
        @JsonProperty("interview_result_id")
        @Schema(description = "Уникальный идентификатор результата собеседования", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174000")
        InterviewResultId id,

        @JsonProperty("feedback_id")
        @Schema(description = "Уникальный идентификатор отзыва", format = "uuid", example = "123e4567-e89b-12d3-a456-426614174001")
        String feedbackId,

        @JsonProperty("verdict")
        @Schema(description = "Вердикт собеседования", example = "APPROVED")
        Verdict verdict
) {
    public static InterviewResultResponseDto from(InterviewResult result) {
        return new InterviewResultResponseDto(result.getId(), result.getFeedbackId(), result.getVerdict());
    }
}