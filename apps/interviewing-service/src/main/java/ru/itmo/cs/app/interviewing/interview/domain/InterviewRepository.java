package ru.itmo.cs.app.interviewing.interview.domain;

import java.util.List;

import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public interface InterviewRepository {
    Interview findById(InterviewId id);
    List<Interview> findAll();
    void save(Interview interview);
}
