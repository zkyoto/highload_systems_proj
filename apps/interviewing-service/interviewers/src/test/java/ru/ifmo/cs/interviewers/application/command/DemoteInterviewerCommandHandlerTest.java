package ru.ifmo.cs.interviewers.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.InterviewerRepository;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;

import static org.mockito.Mockito.*;

class DemoteInterviewerCommandHandlerTest {

    private DemoteInterviewerCommandHandler commandHandler;

    @Mock
    private InterviewerRepository interviewerRepository;

    @Mock
    private Interviewer interviewer;

    @Mock
    private InterviewerId interviewerId;

    private DemoteInterviewerCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commandHandler = new DemoteInterviewerCommandHandler(interviewerRepository);
        command = new DemoteInterviewerCommand(interviewerId);
        when(interviewerRepository.findById(interviewerId)).thenReturn(interviewer);
    }

    @Test
    void testHandleDemotesInterviewerAndSaves() {
        commandHandler.handle(command);

        verify(interviewer, times(1)).demote();
        verify(interviewerRepository, times(1)).save(interviewer);
    }

}