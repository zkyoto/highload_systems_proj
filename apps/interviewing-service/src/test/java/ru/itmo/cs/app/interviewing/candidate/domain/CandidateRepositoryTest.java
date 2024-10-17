package ru.itmo.cs.app.interviewing.candidate.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CandidateRepositoryTest {
    private CandidateRepository candidateRepository;

    @BeforeEach
    public void setUp() {
        candidateRepository = Mockito.mock(CandidateRepository.class);
    }

    @Test
    public void testFindById() {
        CandidateId candidateId = CandidateId.generate();
        Candidate mockCandidate = Mockito.mock(Candidate.class);
        when(candidateRepository.findById(candidateId)).thenReturn(mockCandidate);

        Candidate result = candidateRepository.findById(candidateId);

        assertEquals(mockCandidate, result);
        verify(candidateRepository, times(1)).findById(candidateId);
    }

    @Test
    public void testFindAll() {
        Candidate mockCandidate1 = Mockito.mock(Candidate.class);
        Candidate mockCandidate2 = Mockito.mock(Candidate.class);
        List<Candidate> mockCandidates = Arrays.asList(mockCandidate1, mockCandidate2);
        when(candidateRepository.findAll()).thenReturn(mockCandidates);

        List<Candidate> result = candidateRepository.findAll();

        assertEquals(mockCandidates, result);
        verify(candidateRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        Candidate mockCandidate = Mockito.mock(Candidate.class);

        candidateRepository.save(mockCandidate);

        verify(candidateRepository, times(1)).save(mockCandidate);
    }

}