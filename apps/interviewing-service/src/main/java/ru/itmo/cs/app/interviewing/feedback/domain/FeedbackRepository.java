package ru.itmo.cs.app.interviewing.feedback.domain;

import java.util.List;

import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

public interface FeedbackRepository {
    Feedback findById(FeedbackId id);
    List<Feedback> findAll();
    void save(Feedback feedback);
}
