package ru.itmo.cs.app.interviewing.candidate.domain;

import java.util.List;

import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;

public interface CandidateRepository {
    Candidate findById(CandidateId id);
    List<Candidate> findAll();
    void save(Candidate candidate);
}
