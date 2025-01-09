package ru.ifmo.cs.interview_results.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.value.InterviewResultId;
import ru.ifmo.cs.interview_results.domain.value.Verdict;

public record InterviewResultResponseDto(
        @JsonProperty("interview_result_id") InterviewResultId id,
        @JsonProperty("feedback_id") String feedbackId,
        @JsonProperty("verdict") Verdict verdict
) {
    public static InterviewResultResponseDto from(InterviewResult result) {
        return new InterviewResultResponseDto(result.getId(), result.getFeedbackId(), result.getVerdict());
    }
}
