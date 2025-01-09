package ru.ifmo.cs.candidates.infrastructure.in_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.candidates.application.query.CandidatePageQueryService;
import ru.ifmo.cs.candidates.application.query.dto.CandidatePage;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;

@Service
public class InMemoryStubCandidatePageQueryService implements CandidatePageQueryService {
    private final CandidateRepository candidateRepository;

    public InMemoryStubCandidatePageQueryService(
            @Qualifier("inMemoryStubCandidateRepository") CandidateRepository candidateRepository
    ) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public CandidatePage findFor(int page, int size) {
        List<Candidate> allCandidates = candidateRepository.findAll();
        int from = page * size;
        int to = Math.min(from + size, allCandidates.size());
        List<Candidate> content = allCandidates.subList(from, to);
        return new CandidatePage(content,
                                 page,
                                 size,
                                 allCandidates.size());
    }

}
