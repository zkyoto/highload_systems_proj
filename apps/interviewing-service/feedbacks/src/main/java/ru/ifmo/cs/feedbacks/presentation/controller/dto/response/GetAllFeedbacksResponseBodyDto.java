package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public record GetAllFeedbacksResponseBodyDto(
        @JsonValue List<FeedbackResponseDto> list
) {}
