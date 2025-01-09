package ru.ifmo.cs.candidates.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;
import ru.ifmo.cs.misc.Name;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class AddCandidateCommandHandlerTest {

    private CandidateRepository candidateRepository;
    private AddCandidateCommandHandler addCandidateCommandHandler;

    @BeforeEach
    public void setUp() {
        candidateRepository = Mockito.mock(CandidateRepository.class);
        addCandidateCommandHandler = new AddCandidateCommandHandler(candidateRepository);
    }

    @Test
    public void testHandle() {
        Name candidateName = Name.of("John Doe");
        AddCandidateCommand command = new AddCandidateCommand(candidateName);

        addCandidateCommandHandler.handle(command);

        ArgumentCaptor<Candidate> candidateCaptor = ArgumentCaptor.forClass(Candidate.class);
        verify(candidateRepository).save(candidateCaptor.capture());

        Candidate capturedCandidate = candidateCaptor.getValue();
        assertEquals(candidateName, capturedCandidate.getName());
    }
}