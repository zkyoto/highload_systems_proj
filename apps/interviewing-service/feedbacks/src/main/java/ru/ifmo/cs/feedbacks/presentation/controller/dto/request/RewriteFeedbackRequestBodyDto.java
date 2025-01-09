package ru.ifmo.cs.feedbacks.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RewriteFeedbackRequestBodyDto(
        @JsonProperty("feedback_id") String feedbackId,
        @JsonProperty("grade") Integer grade,
        @JsonProperty("commend") String comment
) {}
