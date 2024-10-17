package ru.itmo.cs.app.interviewing.feedback.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateFeedbackForInterviewCommandHandlerTest {

    private FeedbackRepository feedbackRepository;
    private CreateFeedbackForInterviewCommandHandler handler;

    @BeforeEach
    void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        handler = new CreateFeedbackForInterviewCommandHandler(feedbackRepository);
    }

    @Test
    void testHandleCreatesAndSavesFeedback() {
        InterviewId interviewId = mock(InterviewId.class);
        CreateFeedbackForInterviewCommand command = new CreateFeedbackForInterviewCommand(interviewId);

        handler.handle(command);

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());

        Feedback savedFeedback = feedbackCaptor.getValue();
        assertNotNull(savedFeedback);
        assertEquals(interviewId, savedFeedback.getInterviewId());
    }
}