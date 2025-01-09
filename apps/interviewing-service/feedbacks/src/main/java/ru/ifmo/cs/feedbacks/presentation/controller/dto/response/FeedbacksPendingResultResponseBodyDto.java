package ru.ifmo.cs.feedbacks.presentation.controller.dto.response;

import java.util.List;

public record FeedbacksPendingResultResponseBodyDto(
    List<FeedbackPendingResultDto> feedbackPendingResultDtoList
) {}

