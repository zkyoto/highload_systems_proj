package ru.itmo.cs.app.interviewing.interviewer.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportClient;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddInterviewerCommandHandlerTest {

    private AddInterviewerCommandHandler commandHandler;

    @Mock
    private InterviewerRepository interviewerRepository;

    @Mock
    private PassportClient passportClient;

    @Mock
    private UserId userId;

    private AddInterviewerCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commandHandler = new AddInterviewerCommandHandler(interviewerRepository, passportClient);
        command = new AddInterviewerCommand(userId);
    }

    @Test
    void testHandleSavesInterviewer() {
        Name name = Name.of("John", "Doe");
        when(passportClient.getNameByUserId(userId)).thenReturn(name);

        commandHandler.handle(command);

        ArgumentCaptor<Interviewer> captor = ArgumentCaptor.forClass(Interviewer.class);
        verify(interviewerRepository).save(captor.capture());

        Interviewer savedInterviewer = captor.getValue();
        assertEquals(userId, savedInterviewer.getUserId());
        assertEquals(name, savedInterviewer.getName());
    }

    @Test
    void testGetUserNameFromPassportClient() {
        commandHandler.handle(command);
        verify(passportClient, times(1)).getNameByUserId(userId);
    }
}