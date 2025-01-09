package ru.ifmo.cs.feedbacks.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateFeedbackRequestBodyDto(
        @JsonProperty("interview_id") String interviewId
) {}
