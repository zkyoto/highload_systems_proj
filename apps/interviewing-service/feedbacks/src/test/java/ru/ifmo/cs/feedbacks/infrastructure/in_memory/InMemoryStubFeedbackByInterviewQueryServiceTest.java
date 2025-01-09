package ru.ifmo.cs.feedbacks.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryStubFeedbackByInterviewQueryServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    private InMemoryStubFeedbackByInterviewQueryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new InMemoryStubFeedbackByInterviewQueryService(feedbackRepository);
    }

    @Test
    void testFindByInterviewIdReturnsFeedback() {
        Feedback feedback = mock(Feedback.class);

        when(feedback.getInterviewId()).thenReturn("interviewId");
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback));

        Optional<Feedback> result = service.findByInterviewId("interviewId");

        assertTrue(result.isPresent());
        assertEquals(feedback, result.get());
    }

    @Test
    void testFindByInterviewIdReturnsEmptyOptionalWhenNoMatch() {
        Feedback feedback = mock(Feedback.class);

        when(feedback.getInterviewId()).thenReturn("otherInterviewId");
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback));

        Optional<Feedback> result = service.findByInterviewId("interviewId");

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByInterviewIdReturnsEmptyOptionalWhenRepositoryIsEmpty() {

        when(feedbackRepository.findAll()).thenReturn(Collections.emptyList());

        Optional<Feedback> result = service.findByInterviewId("interviewId");

        assertFalse(result.isPresent());
    }
}