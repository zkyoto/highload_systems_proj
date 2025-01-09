package ru.ifmo.cs.candidates.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.event.CandidateCreatedEvent;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryStubCandidateRepositoryTest {

    private StoredDomainEventRepository storedDomainEventRepository;
    private InMemoryStubCandidateRepository candidateRepository;

    @BeforeEach
    void setUp() {
        storedDomainEventRepository = Mockito.mock(StoredDomainEventRepository.class);
        candidateRepository = new InMemoryStubCandidateRepository(storedDomainEventRepository);
    }

    @Test
    void testSaveNewCandidate() {
        CandidateId candidateId = new CandidateId(UUID.randomUUID());
        Candidate candidate = Mockito.mock(Candidate.class);
        when(candidate.getId()).thenReturn(candidateId);
        when(candidate.releaseEvents()).thenReturn(List.of(Mockito.mock(CandidateCreatedEvent.class)));

        candidateRepository.save(candidate);

        Candidate found = candidateRepository.findById(candidateId);
        assertNotNull(found);
        assertEquals(candidateId, found.getId());
        verify(storedDomainEventRepository).save(any());
    }

    @Test
    void testFindById() {
        CandidateId candidateId = new CandidateId(UUID.randomUUID());
        Candidate candidate = Mockito.mock(Candidate.class);
        when(candidate.getId()).thenReturn(candidateId);
        when(candidate.releaseEvents()).thenReturn(List.of(Mockito.mock(CandidateCreatedEvent.class)));
        candidateRepository.save(candidate);

        Candidate found = candidateRepository.findById(candidateId);

        assertNotNull(found);
        assertEquals(candidateId, found.getId());
    }

    @Test
    void testFindAll() {
        CandidateId candidateId1 = new CandidateId(UUID.randomUUID());
        Candidate candidate1 = Mockito.mock(Candidate.class);
        when(candidate1.getId()).thenReturn(candidateId1);
        when(candidate1.releaseEvents()).thenReturn(List.of(Mockito.mock(CandidateCreatedEvent.class)));

        CandidateId candidateId2 = new CandidateId(UUID.randomUUID());
        Candidate candidate2 = Mockito.mock(Candidate.class);
        when(candidate2.getId()).thenReturn(candidateId2);
        when(candidate2.releaseEvents()).thenReturn(List.of(Mockito.mock(CandidateCreatedEvent.class)));

        candidateRepository.save(candidate1);
        candidateRepository.save(candidate2);

        var allCandidates = candidateRepository.findAll();

        assertEquals(2, allCandidates.size());
        assertTrue(allCandidates.contains(candidate1));
        assertTrue(allCandidates.contains(candidate2));
    }

    @Test
    void testUpdateCandidate() {
        CandidateId candidateId = new CandidateId(UUID.randomUUID());
        Candidate candidate = Mockito.mock(Candidate.class);
        when(candidate.getId()).thenReturn(candidateId);
        when(candidate.releaseEvents()).thenReturn(List.of(Mockito.mock(CandidateCreatedEvent.class)));
        candidateRepository.save(candidate);

        Candidate updatedCandidate = Mockito.mock(Candidate.class);
        when(updatedCandidate.getId()).thenReturn(candidateId);
        when(updatedCandidate.releaseEvents()).thenReturn(List.of());

        candidateRepository.save(updatedCandidate);

        Candidate found = candidateRepository.findById(candidateId);
        assertEquals(updatedCandidate, found);
    }

    @Test
    void testUpdateNonexistentCandidateThrowsException() {
        CandidateId candidateId = new CandidateId(UUID.randomUUID());
        Candidate candidate = Mockito.mock(Candidate.class);
        when(candidate.getId()).thenReturn(candidateId);
        when(candidate.releaseEvents()).thenReturn(List.of());

        assertThrows(IllegalStateException.class, () -> candidateRepository.save(candidate));
    }
}