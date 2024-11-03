package ru.itmo.cs.app.interviewing.feedback.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;

public record FeedbackPage(
        List<Feedback> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Feedback> {}
