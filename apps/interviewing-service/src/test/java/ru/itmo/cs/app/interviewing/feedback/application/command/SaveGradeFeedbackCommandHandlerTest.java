package ru.itmo.cs.app.interviewing.feedback.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaveGradeFeedbackCommandHandlerTest {

    private FeedbackRepository feedbackRepository;
    private SaveGradeFeedbackCommandHandler handler;

    @BeforeEach
    void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        handler = new SaveGradeFeedbackCommandHandler(feedbackRepository);
    }

    @Test
    void testHandleUpdatesGradeAndSavesFeedback() {
        FeedbackId feedbackId = mock(FeedbackId.class);
        Grade newGrade = mock(Grade.class);

        Feedback feedback = mock(Feedback.class);
        when(feedbackRepository.findById(feedbackId)).thenReturn(feedback);

        SaveGradeFeedbackCommand command = new SaveGradeFeedbackCommand(feedbackId, newGrade);
        handler.handle(command);

        verify(feedback).setGrade(newGrade);

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());

        Feedback savedFeedback = feedbackCaptor.getValue();
        assertNotNull(savedFeedback);
    }

    @Test
    void testHandleThrowsExceptionIfFeedbackNotFound() {
        FeedbackId feedbackId = mock(FeedbackId.class);
        Grade grade = mock(Grade.class);

        SaveGradeFeedbackCommand command = new SaveGradeFeedbackCommand(feedbackId, grade);

        when(feedbackRepository.findById(feedbackId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> handler.handle(command));
    }
}