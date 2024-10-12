package ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateFeedbackRequestBodyDto(
        @JsonProperty("interview_id") String interviewId
) {}
