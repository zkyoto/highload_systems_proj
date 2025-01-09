package ru.ifmo.cs.feedbacks.application.query;

import java.util.List;

import ru.ifmo.cs.feedbacks.domain.Feedback;

public interface FeedbacksPendingResultQueryService {
    List<Feedback> findAll();
}
