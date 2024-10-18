package ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.Verdict;

public record InterviewResultResponseDto(
        @JsonProperty("interview_result_id") InterviewResultId id,
        @JsonProperty("feedback_id") FeedbackId feedbackId,
        @JsonProperty("verdict") Verdict verdict
) {
    public static InterviewResultResponseDto from(InterviewResult result) {
        return new InterviewResultResponseDto(result.getId(), result.getFeedbackId(), result.getVerdict());
    }
}
