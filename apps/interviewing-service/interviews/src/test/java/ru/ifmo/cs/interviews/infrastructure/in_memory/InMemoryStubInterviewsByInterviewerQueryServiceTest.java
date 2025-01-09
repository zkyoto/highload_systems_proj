package ru.ifmo.cs.interviews.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.interviews.application.query.dto.InterviewsByInterviewerDto;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;

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
        Interview interview1 = mock(Interview.class);
        Interview interview2 = mock(Interview.class);
        Interview interview3 = mock(Interview.class);

        when(interview1.getInterviewerId()).thenReturn("interviewerId");
        when(interview2.getInterviewerId()).thenReturn("interviewerId");
        when(interview3.getInterviewerId()).thenReturn("otherInterviewerId");

        List<Interview> allInterviews = List.of(interview1, interview2, interview3);
        when(interviewRepository.findAll()).thenReturn(allInterviews);

        InterviewsByInterviewerDto result = service.findFor("interviewerId");

        assertEquals("interviewerId", result.interviewerId());
        assertEquals(2, result.interviews().size());
        assertEquals(List.of(interview1, interview2), result.interviews());
    }

    @Test
    void testFindByReturnsEmptyListWhenNoInterviewsForInterviewer() {
        Interview otherInterview = mock(Interview.class);
        when(otherInterview.getInterviewerId()).thenReturn("otherInteviewerId"); // Другая личность

        when(interviewRepository.findAll()).thenReturn(List.of(otherInterview));

        InterviewsByInterviewerDto result = service.findFor("interviewerId");

        assertEquals("interviewerId", result.interviewerId());
        assertEquals(0, result.interviews().size());
    }

    @Test
    void testFindByReturnsEmptyListForEmptyRepository() {
        when(interviewRepository.findAll()).thenReturn(List.of());  // Пустое хранилище

        InterviewsByInterviewerDto result = service.findFor("interviewerId");

        assertEquals("interviewerId", result.interviewerId());
        assertEquals(0, result.interviews().size());
    }
}