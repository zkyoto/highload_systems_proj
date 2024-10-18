package ru.itmo.cs.app.interviewing.feedback.application.query.dto;

import java.util.List;

import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.libs.page.Page;

public record FeedbackPage(
        List<Feedback> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Feedback> {}
