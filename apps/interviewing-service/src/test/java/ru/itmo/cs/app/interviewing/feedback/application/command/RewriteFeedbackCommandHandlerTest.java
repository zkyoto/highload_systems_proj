package ru.itmo.cs.app.interviewing.feedback.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewriteFeedbackCommandHandlerTest {

    private FeedbackRepository feedbackRepository;
    private RewriteFeedbackCommandHandler handler;

    @BeforeEach
    void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        handler = new RewriteFeedbackCommandHandler(feedbackRepository);
    }

    @Test
    void testHandleRewritesAndSavesFeedback() {
        FeedbackId feedbackId = mock(FeedbackId.class);
        Grade newGrade = mock(Grade.class);
        Comment newComment = mock(Comment.class);

        Feedback feedback = mock(Feedback.class);
        when(feedbackRepository.findById(feedbackId)).thenReturn(feedback);

        RewriteFeedbackCommand command = new RewriteFeedbackCommand(feedbackId, newGrade, newComment);
        handler.handle(command);

        verify(feedback).rewrite(newGrade, newComment);

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());

        Feedback savedFeedback = feedbackCaptor.getValue();
        assertNotNull(savedFeedback);
    }

    @Test
    void testHandleThrowsExceptionIfFeedbackNotFound() {
        FeedbackId feedbackId = mock(FeedbackId.class);
        Grade grade = mock(Grade.class);
        Comment comment = mock(Comment.class);

        RewriteFeedbackCommand command = new RewriteFeedbackCommand(feedbackId, grade, comment);

        when(feedbackRepository.findById(feedbackId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> handler.handle(command));
    }
}