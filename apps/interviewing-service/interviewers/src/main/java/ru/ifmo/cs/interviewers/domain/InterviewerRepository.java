package ru.ifmo.cs.interviewers.domain;

import java.util.List;

import ru.ifmo.cs.interviewers.domain.value.InterviewerId;


public interface InterviewerRepository {
    Interviewer findById(InterviewerId id);
    List<Interviewer> findAll();
    void save(Interviewer interviewer);
}
