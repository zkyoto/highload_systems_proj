package ru.itmo.cs.app.interviewing.interview_result.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.Verdict;

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
        FeedbackId feedbackId = mock(FeedbackId.class);
        Verdict verdict = mock(Verdict.class);

        CreateInterviewResultCommand command = new CreateInterviewResultCommand(feedbackId, verdict);

        handler.handle(command);

        ArgumentCaptor<InterviewResult> interviewResultCaptor = ArgumentCaptor.forClass(InterviewResult.class);
        verify(interviewResultRepository).save(interviewResultCaptor.capture());

        InterviewResult savedInterviewResult = interviewResultCaptor.getValue();
        assertEquals(feedbackId, savedInterviewResult.getFeedbackId());
        assertEquals(verdict, savedInterviewResult.getVerdict());
    }
}