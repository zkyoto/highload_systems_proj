package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetFeedbackResponseBodyDto(
        @JsonValue FeedbackResponseDto feedbackResponseDto
) {}
