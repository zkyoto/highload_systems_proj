package ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetFeedbackResponseBodyDto(
        @JsonValue FeedbackResponseDto feedbackResponseDto
) {}
