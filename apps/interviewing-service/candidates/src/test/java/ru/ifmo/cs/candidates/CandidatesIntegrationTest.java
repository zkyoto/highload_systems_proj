package ru.ifmo.cs.candidates;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;
import ru.ifmo.cs.integration_tests.AbstractIntegrationTest;
import ru.ifmo.cs.misc.Name;

public class CandidatesIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate createCandidate() {
        Candidate stubCandidate = Candidate.create(Name.of("test name"));
        candidateRepository.save(stubCandidate);
        return stubCandidate;
    }
}
