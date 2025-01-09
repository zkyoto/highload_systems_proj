package ru.ifmo.cs.candidates.application.query;

import ru.ifmo.cs.candidates.domain.Candidate;

public interface CandidateByInterviewResultQueryService {
    Candidate findFor(String interviewResultId);
}
