package ru.itmo.cs.app.interviewing.feedback.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.itmo.cs.app.interviewing.feedback.application.query.dto.FeedbackPage;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStubFeedbackPageQueryServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private InMemoryStubFeedbackPageQueryService feedbackPageQueryService;

    @Mock
    private Feedback feedback1;

    @Mock
    private Feedback feedback2;

    @Mock
    private Feedback feedback3;

    private List<Feedback> feedbacks;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        feedbacks = Arrays.asList(feedback1, feedback2, feedback3);

        Mockito.when(feedbackRepository.findAll()).thenReturn(feedbacks);
    }

    @Test
    public void testFindForReturnsCorrectPage() {
        int page = 0;
        int size = 2;
        FeedbackPage result = feedbackPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(feedback1, result.content().get(0));
        assertEquals(feedback2, result.content().get(1));
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(feedbacks.size(), result.totalElements());
    }

    @Test
    public void testFindForPageOutOfBoundsReturnsEmptyPage() {
        int page = 1;
        int size = 3;

        FeedbackPage result = feedbackPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(feedbacks.size(), result.totalElements());
    }
}