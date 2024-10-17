package ru.itmo.cs.app.interviewing.feedback.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryStubFeedbackRepositoryTest {

    @Mock
    private StoredDomainEventRepository storedDomainEventRepository;

    private FeedbackRepository feedbackRepository;

    private Feedback feedback;
    private FeedbackId feedbackId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feedbackRepository = new InMemoryStubFeedbackRepository(storedDomainEventRepository);

        feedbackId = mock(FeedbackId.class);
        feedback = mock(Feedback.class);
        when(feedback.getId()).thenReturn(feedbackId);
        when(feedback.releaseEvents()).thenReturn(List.of(mock(FeedbackCreatedEvent.class)));
    }

    @Test
    void testFindByIdReturnsFeedback() {
        feedbackRepository.save(feedback);
        Feedback foundFeedback = feedbackRepository.findById(feedbackId);
        assertEquals(feedback, foundFeedback);
    }

    @Test
    void testFindByIdThrowsExceptionWhenFeedbackNotFound() {
        assertThrows(NoSuchElementException.class, () -> feedbackRepository.findById(feedbackId));
    }

    @Test
    void testFindAllReturnsAllFeedbacks() {
        feedbackRepository.save(feedback);
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertEquals(1, feedbacks.size());
        assertTrue(feedbacks.contains(feedback));
    }

    @Test
    void testSaveInsertsNewFeedback() {
        feedbackRepository.save(feedback);

        assertEquals(1, feedbackRepository.findAll().size());
        verify(storedDomainEventRepository, times(1)).save(any(StoredDomainEvent.class));
    }

    @Test
    void testSaveUpdatesExistingFeedback() {
        feedbackRepository.save(feedback);
        when(feedback.releaseEvents()).thenReturn(List.of());
        feedbackRepository.save(feedback);  // Should update

        assertEquals(1, feedbackRepository.findAll().size());
        verify(storedDomainEventRepository, times(1)).save(any(StoredDomainEvent.class));
    }
}