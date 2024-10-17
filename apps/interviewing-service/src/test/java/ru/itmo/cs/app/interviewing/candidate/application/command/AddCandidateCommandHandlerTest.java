package ru.itmo.cs.app.interviewing.candidate.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
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