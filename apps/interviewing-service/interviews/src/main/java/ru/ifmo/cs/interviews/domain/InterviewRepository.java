package ru.ifmo.cs.interviews.domain;

import java.util.List;

import ru.ifmo.cs.interviews.domain.value.InterviewId;

public interface InterviewRepository {
    Interview findById(InterviewId id);
    List<Interview> findAll();
    void save(Interview interview);
}
