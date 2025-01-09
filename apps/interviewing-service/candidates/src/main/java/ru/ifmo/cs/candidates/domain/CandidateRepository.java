package ru.ifmo.cs.candidates.domain;

import java.util.List;

import ru.ifmo.cs.candidates.domain.value.CandidateId;

public interface CandidateRepository {
    Candidate findById(CandidateId id);
    List<Candidate> findAll();
    void save(Candidate candidate);
}
