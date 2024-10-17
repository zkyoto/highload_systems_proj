package ru.itmo.cs.app.interviewing.interview.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itmo.cs.app.interviewing.interview.application.query.dto.InterviewsByInterviewerDto;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InMemoryStubInterviewsByInterviewerQueryServiceTest {

    @Mock
    private InterviewRepository interviewRepository;

    private InMemoryStubInterviewsByInterviewerQueryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new InMemoryStubInterviewsByInterviewerQueryService(interviewRepository);
    }

    @Test
    void testFindByReturnsCorrectInterviewsForInterviewer() {
        InterviewerId interviewerId = mock(InterviewerId.class);
        InterviewerId otherInterviewerId = mock(InterviewerId.class);

        Interview interview1 = mock(Interview.class);
        Interview interview2 = mock(Interview.class);
        Interview interview3 = mock(Interview.class);

        when(interview1.getInterviewerId()).thenReturn(interviewerId);
        when(interview2.getInterviewerId()).thenReturn(interviewerId);
        when(interview3.getInterviewerId()).thenReturn(otherInterviewerId);

        List<Interview> allInterviews = List.of(interview1, interview2, interview3);
        when(interviewRepository.findAll()).thenReturn(allInterviews);

        InterviewsByInterviewerDto result = service.findBy(interviewerId);

        assertEquals(interviewerId, result.interviewerId());
        assertEquals(2, result.interviews().size());
        assertEquals(List.of(interview1, interview2), result.interviews());
    }

    @Test
    void testFindByReturnsEmptyListWhenNoInterviewsForInterviewer() {
        InterviewerId interviewerId = mock(InterviewerId.class);
        Interview otherInterview = mock(Interview.class);
        when(otherInterview.getInterviewerId()).thenReturn(mock(InterviewerId.class)); // Другая личность

        when(interviewRepository.findAll()).thenReturn(List.of(otherInterview));

        InterviewsByInterviewerDto result = service.findBy(interviewerId);

        assertEquals(interviewerId, result.interviewerId());
        assertEquals(0, result.interviews().size());
    }

    @Test
    void testFindByReturnsEmptyListForEmptyRepository() {
        InterviewerId interviewerId = mock(InterviewerId.class);

        when(interviewRepository.findAll()).thenReturn(List.of());  // Пустое хранилище

        InterviewsByInterviewerDto result = service.findBy(interviewerId);

        assertEquals(interviewerId, result.interviewerId());
        assertEquals(0, result.interviews().size());
    }
}