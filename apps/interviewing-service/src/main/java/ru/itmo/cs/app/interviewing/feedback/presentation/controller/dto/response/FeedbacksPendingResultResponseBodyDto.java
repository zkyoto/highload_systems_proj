package ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response;

import java.util.List;

public record FeedbacksPendingResultResponseBodyDto(
    List<FeedbackPendingResultDto> feedbackPendingResultDtoList
) {}

