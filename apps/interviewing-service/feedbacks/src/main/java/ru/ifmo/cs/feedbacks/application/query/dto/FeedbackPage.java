package ru.ifmo.cs.feedbacks.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.ifmo.cs.feedbacks.domain.Feedback;

public record FeedbackPage(
        List<Feedback> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Feedback> {}
