package ru.ifmo.cs.feedbacks.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;

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
        CreateFeedbackForInterviewCommand command = new CreateFeedbackForInterviewCommand("interviewId");

        handler.handle(command);

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());

        Feedback savedFeedback = feedbackCaptor.getValue();
        assertNotNull(savedFeedback);
        assertEquals("interviewId", savedFeedback.getInterviewId());
    }
}