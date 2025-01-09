package ru.ifmo.cs.interview_results.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InMemoryStubInterviewResultByFeedbackQueryServiceTest {

    private InMemoryStubInterviewResultByFeedbackQueryService queryService;

    @Mock
    private InterviewResultRepository interviewResultRepository;

    @Mock
    private InterviewResult interviewResult1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        queryService = new InMemoryStubInterviewResultByFeedbackQueryService(interviewResultRepository);

        when(interviewResult1.getFeedbackId()).thenReturn("feedbackId1");
    }

    @Test
    void testFindByFeedbackIdReturnsInterviewResult() {
        when(interviewResultRepository.findAll()).thenReturn(List.of(interviewResult1));

        Optional<InterviewResult> result = queryService.findByFeedbackId("feedbackId1");
        assertTrue(result.isPresent());
        assertEquals(interviewResult1, result.get());
    }

    @Test
    void testFindByFeedbackIdReturnsEmptyWhenNoMatch() {
        when(interviewResultRepository.findAll()).thenReturn(List.of(interviewResult1));

        Optional<InterviewResult> result = queryService.findByFeedbackId("unknownFeedbackId");
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByFeedbackIdWithEmptyRepository() {
        when(interviewResultRepository.findAll()).thenReturn(List.of());

        Optional<InterviewResult> result = queryService.findByFeedbackId("feedbackId1");
        assertFalse(result.isPresent());
    }
}