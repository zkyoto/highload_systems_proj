package ru.ifmo.cs.feedbacks.domain;

import java.util.List;

import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;

public interface FeedbackRepository {
    Feedback findById(FeedbackId id);
    List<Feedback> findAll();
    void save(Feedback feedback);
}
