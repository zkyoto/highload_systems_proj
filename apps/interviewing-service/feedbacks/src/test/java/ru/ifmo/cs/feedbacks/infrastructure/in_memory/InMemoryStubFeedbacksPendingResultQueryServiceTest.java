//package ru.ifmo.cs.feedbacks.infrastructure.in_memory;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import ru.ifmo.cs.feedbacks.domain.Feedback;
//import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
//import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
//import ru.ifmo.cs.feedbacks.domain.value.FeedbackStatus;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class InMemoryStubFeedbacksPendingResultQueryServiceTest {
//
//    @Mock
//    private FeedbackRepository feedbackRepository;
//
//    @Mock
//    private InterviewResultRepository interviewResultRepository;
//
//    private InMemoryStubFeedbacksPendingResultQueryService service;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        service = new InMemoryStubFeedbacksPendingResultQueryService(feedbackRepository, interviewResultRepository);
//    }
//
//    @Test
//    void testFindAllReturnsFeedbacksPendingResult() {
//        FeedbackId feedbackId1 = mock(FeedbackId.class);
//        FeedbackId feedbackId2 = mock(FeedbackId.class);
//
//        Feedback feedback1 = mock(Feedback.class);
//        Feedback feedback2 = mock(Feedback.class);
//        Feedback feedback3 = mock(Feedback.class);
//
//        when(feedback1.getId()).thenReturn(feedbackId1);
//        when(feedback1.getStatus()).thenReturn(FeedbackStatus.SUBMITTED);
//
//        when(feedback2.getId()).thenReturn(feedbackId2);
//        when(feedback2.getStatus()).thenReturn(FeedbackStatus.SUBMITTED);
//
//        when(feedback3.getStatus()).thenReturn(FeedbackStatus.WAITING_FOR_SUBMIT);
//
//        InterviewResult result1 = mock(InterviewResult.class);
//        when(result1.getFeedbackId()).thenReturn(feedbackId1);
//
//        when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2, feedback3));
//        when(interviewResultRepository.findAll()).thenReturn(List.of(result1));
//
//        List<Feedback> feedbacksPendingResult = service.findAll();
//
//        assertEquals(1, feedbacksPendingResult.size());
//        assertEquals(feedback2, feedbacksPendingResult.get(0));
//    }
//
//    @Test
//    void testFindAllReturnsEmptyWhenNoFeedbacksPendingResult() {
//        Feedback feedback1 = mock(Feedback.class);
//
//        when(feedbackRepository.findAll()).thenReturn(List.of(feedback1));
//        when(feedback1.getStatus()).thenReturn(FeedbackStatus.WAITING_FOR_SUBMIT);
//
//        when(interviewResultRepository.findAll()).thenReturn(List.of());
//
//        List<Feedback> feedbacksPendingResult = service.findAll();
//
//        assertEquals(0, feedbacksPendingResult.size());
//    }
//}