package ru.ifmo.cs.interview_results.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;
import ru.ifmo.cs.interview_results.domain.value.Verdict;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateInterviewResultCommandHandlerTest {

    @Mock
    private InterviewResultRepository interviewResultRepository;

    private CreateInterviewResultCommandHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreateInterviewResultCommandHandler(interviewResultRepository);
    }

    @Test
    void testHandleCreatesAndSavesInterviewResult() {
        Verdict verdict = mock(Verdict.class);

        CreateInterviewResultCommand command = new CreateInterviewResultCommand("feedbackId", verdict);

        handler.handle(command);

        ArgumentCaptor<InterviewResult> interviewResultCaptor = ArgumentCaptor.forClass(InterviewResult.class);
        verify(interviewResultRepository).save(interviewResultCaptor.capture());

        InterviewResult savedInterviewResult = interviewResultCaptor.getValue();
        assertEquals("feedbackId", savedInterviewResult.getFeedbackId());
        assertEquals(verdict, savedInterviewResult.getVerdict());
    }
}