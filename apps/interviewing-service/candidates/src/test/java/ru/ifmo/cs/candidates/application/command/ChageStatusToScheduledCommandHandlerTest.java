package ru.ifmo.cs.candidates.application.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;
import ru.ifmo.cs.candidates.domain.value.CandidateId;

public class ChageStatusToScheduledCommandHandlerTest {

    private ChageStatusToScheduledCommandHandler handler;

    @Mock
    private CandidateRepository candidateRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ChageStatusToScheduledCommandHandler(candidateRepository);
    }

    @Test
    public void testHandle() {
        CandidateId candidateId = CandidateId.generate();
        ChageStatusToScheduledCommand command = new ChageStatusToScheduledCommand(candidateId);
        Candidate mockCandidate = mock(Candidate.class);

        when(candidateRepository.findById(candidateId)).thenReturn(mockCandidate);

        handler.handle(command);

        verify(candidateRepository).findById(candidateId);
        verify(mockCandidate).changeStatusToScheduled();
        verify(candidateRepository).save(mockCandidate);
    }
}