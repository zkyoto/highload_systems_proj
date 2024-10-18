package ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackStatus;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public record FeedbackResponseDto(
        @JsonProperty("feedback_id") FeedbackId id,
        @JsonProperty("interview_id") InterviewId interviewId,
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
