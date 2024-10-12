package ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubmitFeedbackRequestBodyDto(
        @JsonProperty("feedback_id") String feedbackId
) {}
