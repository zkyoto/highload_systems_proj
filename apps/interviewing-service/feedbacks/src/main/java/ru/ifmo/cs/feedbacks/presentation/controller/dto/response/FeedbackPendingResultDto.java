package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.Grade;

public record FeedbackPendingResultDto(
        @JsonProperty("feedback_id") FeedbackId feedbackId,
        @JsonProperty("interview_id") String interviewId,
        @JsonProperty("grade") Grade grade,
        @JsonProperty("comment") Comment comment
) {
    public static FeedbackPendingResultDto from(Feedback feedback) {
        return new FeedbackPendingResultDto(feedback.getId(),
                                            feedback.getInterviewId(),
                                            feedback.getGrade(),
                                            feedback.getComment());
    }
}
