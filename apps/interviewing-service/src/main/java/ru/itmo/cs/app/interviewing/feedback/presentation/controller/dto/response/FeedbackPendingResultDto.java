package ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public record FeedbackPendingResultDto(
        @JsonProperty("feedback_id") FeedbackId feedbackId,
        @JsonProperty("interview_id") InterviewId interviewId,
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
