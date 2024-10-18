package ru.itmo.cs.app.interviewing.candidate.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.itmo.cs.app.interviewing.candidate.application.query.dto.CandidatePage;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStubCandidatePageQueryServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private InMemoryStubCandidatePageQueryService candidatePageQueryService;

    @Mock
    private Candidate candidate1;

    @Mock
    private Candidate candidate2;

    @Mock
    private Candidate candidate3;

    private List<Candidate> candidates;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        candidates = Arrays.asList(candidate1, candidate2, candidate3);

        Mockito.when(candidateRepository.findAll()).thenReturn(candidates);
    }

    @Test
    public void testFindForReturnsCorrectPage() {
        int page = 0;
        int size = 2;
        CandidatePage result = candidatePageQueryService.findFor(page, size);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(candidate1, result.content().get(0));
        assertEquals(candidate2, result.content().get(1));
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(candidates.size(), result.totalElements());
    }

    @Test
    public void testFindForPageOutOfBoundsReturnsEmptyPage() {
        int page = 1;
        int size = 3;

        CandidatePage result = candidatePageQueryService.findFor(page, size);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(candidates.size(), result.totalElements());
    }
}