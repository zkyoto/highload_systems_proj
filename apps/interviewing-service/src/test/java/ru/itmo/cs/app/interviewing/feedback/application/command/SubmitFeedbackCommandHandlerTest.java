package ru.itmo.cs.app.interviewing.feedback.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubmitFeedbackCommandHandlerTest {

    private FeedbackRepository feedbackRepository;
    private SubmitFeedbackCommandHandler handler;

    @BeforeEach
    void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        handler = new SubmitFeedbackCommandHandler(feedbackRepository);
    }

    @Test
    void testHandleSubmitsAndSavesFeedback() {
        FeedbackId feedbackId = mock(FeedbackId.class);

        Feedback feedback = mock(Feedback.class);
        when(feedbackRepository.findById(feedbackId)).thenReturn(feedback);

        SubmitFeedbackCommand command = new SubmitFeedbackCommand(feedbackId);
        handler.handle(command);

        verify(feedback).submit();

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());

        Feedback savedFeedback = feedbackCaptor.getValue();
        assertNotNull(savedFeedback);
    }

    @Test
    void testHandleThrowsExceptionIfFeedbackNotFound() {
        FeedbackId feedbackId = mock(FeedbackId.class);

        SubmitFeedbackCommand command = new SubmitFeedbackCommand(feedbackId);

        when(feedbackRepository.findById(feedbackId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> handler.handle(command));
    }
}