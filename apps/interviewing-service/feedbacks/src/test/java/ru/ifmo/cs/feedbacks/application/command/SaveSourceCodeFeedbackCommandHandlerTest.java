package ru.ifmo.cs.feedbacks.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.SourceCodeFileId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SaveSourceCodeFeedbackCommandHandlerTest {

    private FeedbackRepository feedbackRepository;
    private SaveSourceCodeFeedbackCommandHandler commandHandler;
    private Feedback feedback;

    @BeforeEach
    public void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        commandHandler = new SaveSourceCodeFeedbackCommandHandler(feedbackRepository);
        feedback = mock(Feedback.class);
    }

    @Test
    public void testHandle() {
        // Arrange
        FeedbackId feedbackId = FeedbackId.generate();
        SourceCodeFileId sourceCodeFileId = new SourceCodeFileId("sourceCode123");
        SaveSourceCodeFeedbackCommand command = new SaveSourceCodeFeedbackCommand(feedbackId, sourceCodeFileId);

        when(feedbackRepository.findById(feedbackId)).thenReturn(feedback);

        // Act
        commandHandler.handle(command);

        // Assert
        verify(feedbackRepository).findById(feedbackId);
        verify(feedback).setSourceCodeFileId(sourceCodeFileId);

        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        assertEquals(feedback, feedbackCaptor.getValue());
    }
}