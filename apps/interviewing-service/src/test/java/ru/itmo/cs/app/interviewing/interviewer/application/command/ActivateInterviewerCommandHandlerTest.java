package ru.itmo.cs.app.interviewing.interviewer.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

import static org.mockito.Mockito.*;

class ActivateInterviewerCommandHandlerTest {

    private ActivateInterviewerCommandHandler commandHandler;

    @Mock
    private InterviewerRepository interviewerRepository;

    @Mock
    private Interviewer interviewer;

    @Mock
    private InterviewerId interviewerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commandHandler = new ActivateInterviewerCommandHandler(interviewerRepository);
        when(interviewerRepository.findById(interviewerId)).thenReturn(interviewer);
    }

    @Test
    void testHandleActivatesInterviewerAndSaves() {
        ActivateInterviewerCommand command = new ActivateInterviewerCommand(interviewerId);
        commandHandler.handle(command);

        verify(interviewer, times(1)).activate();
        verify(interviewerRepository, times(1)).save(interviewer);
    }

    @Test
    void testFindByIdIsCalledWithCorrectId() {
        ActivateInterviewerCommand command = new ActivateInterviewerCommand(interviewerId);

        commandHandler.handle(command);

        verify(interviewerRepository, times(1)).findById(interviewerId);
    }
}