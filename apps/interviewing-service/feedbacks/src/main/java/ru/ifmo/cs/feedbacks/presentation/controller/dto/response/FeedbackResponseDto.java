package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackStatus;
import ru.ifmo.cs.feedbacks.domain.value.Grade;

public record FeedbackResponseDto(
        @JsonProperty("feedback_id") FeedbackId id,
        @JsonProperty("interview_id") String interviewId,
        @JsonProperty("status") FeedbackStatus status,
        @JsonProperty("grade") @Nullable Grade grade,
        @JsonProperty("comment") @Nullable Comment comment
) {
    public static FeedbackResponseDto from(Feedback feedback) {
        return new FeedbackResponseDto(feedback.getId(),
                                       feedback.getInterviewId(),
                                       feedback.getStatus(),
                                       feedback.getGrade(),
                                       feedback.getComment());
    }
}
