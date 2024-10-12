package ru.itmo.cs.app.interviewing.feedback.domain;

import java.util.List;
import java.util.Optional;

import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public interface FeedbackRepository {
    Feedback findById(FeedbackId id);
    List<Feedback> findAll();
    void save(Feedback feedback);
}
