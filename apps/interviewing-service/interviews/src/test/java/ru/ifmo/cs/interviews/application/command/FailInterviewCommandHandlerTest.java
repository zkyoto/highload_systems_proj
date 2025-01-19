package ru.ifmo.cs.interviews.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;
import ru.ifmo.cs.interviews.domain.value.InterviewId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FailInterviewCommandHandlerTest {

    private InterviewRepository interviewRepository;
    private FailInterviewCommandHandler commandHandler;
    private Interview interview;
    private InterviewId interviewId;

    @BeforeEach
    void setUp() {
        interviewRepository = mock(InterviewRepository.class);
        commandHandler = new FailInterviewCommandHandler(interviewRepository);
        interview = mock(Interview.class);
        interviewId = InterviewId.generate(); // замените на реальный конструктор или фабричный метод, если необходим

        when(interviewRepository.findById(interviewId)).thenReturn(interview);
    }

    @Test
    void testHandle() {
        // Arrange
        FailInterviewCommand command = new FailInterviewCommand(interviewId);

        // Act
        commandHandler.handle(command);

        // Assert
        verify(interviewRepository).findById(interviewId);
        verify(interview).fail();
        ArgumentCaptor<Interview> interviewCaptor = ArgumentCaptor.forClass(Interview.class);
        verify(interviewRepository).save(interviewCaptor.capture());
        assertEquals(interview, interviewCaptor.getValue());
    }
}