package ru.itmo.cs.app.interviewing.feedback.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaveCommentFeedbackCommandHandlerTest {

    private FeedbackRepository feedbackRepository;
    private SaveCommentFeedbackCommandHandler handler;

    @BeforeEach
    void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        handler = new SaveCommentFeedbackCommandHandler(feedbackRepository);
    }

    @Test
    void testHandleUpdatesCommentAndSavesFeedback() {
        FeedbackId feedbackId = mock(FeedbackId.class);
        Comment newComment = mock(Comment.class);

        Feedback feedback = mock(Feedback.class);
        when(feedbackRepository.findById(feedbackId)).thenReturn(feedback);

        SaveCommentFeedbackCommand command = new SaveCommentFeedbackCommand(feedbackId, newComment);
        handler.handle(command);

        verify(feedback).setComment(newComment);

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());

        Feedback savedFeedback = feedbackCaptor.getValue();
        assertNotNull(savedFeedback);
    }

    @Test
    void testHandleThrowsExceptionIfFeedbackNotFound() {
        FeedbackId feedbackId = mock(FeedbackId.class);
        Comment comment = mock(Comment.class);

        SaveCommentFeedbackCommand command = new SaveCommentFeedbackCommand(feedbackId, comment);

        when(feedbackRepository.findById(feedbackId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> handler.handle(command));
    }
}