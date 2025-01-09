package ru.ifmo.cs.interview_results.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.interview_results.domain.value.Verdict;

public record CreateInterviewResultRequestBodyDto(
        @JsonProperty("feedback_id") String feedbackId,
        @JsonProperty("verdict") Verdict verdict
) {}
