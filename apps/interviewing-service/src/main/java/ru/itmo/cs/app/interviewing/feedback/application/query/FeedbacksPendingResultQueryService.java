package ru.itmo.cs.app.interviewing.feedback.application.query;

import java.util.List;

import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;

public interface FeedbacksPendingResultQueryService {
    List<Feedback> findAll();
}
