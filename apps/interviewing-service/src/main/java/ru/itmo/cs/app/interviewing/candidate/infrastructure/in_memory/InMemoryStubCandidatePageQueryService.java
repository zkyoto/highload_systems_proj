package ru.itmo.cs.app.interviewing.candidate.infrastructure.in_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.candidate.application.query.CandidatePageQueryService;
import ru.itmo.cs.app.interviewing.candidate.application.query.dto.CandidatePage;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;

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
