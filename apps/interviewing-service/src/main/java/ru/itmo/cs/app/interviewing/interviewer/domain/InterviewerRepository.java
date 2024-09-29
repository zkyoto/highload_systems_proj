package ru.itmo.cs.app.interviewing.interviewer.domain;

import java.util.List;

import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public interface InterviewerRepository {
    Interviewer findById(InterviewerId id);
    List<Interviewer> findAll();
    void save(Interviewer interviewer);
}
