package ru.itmo.cs.app.interviewing.interview.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.itmo.cs.app.interviewing.interview.application.query.dto.InterviewPage;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStubInterviewPageQueryServiceTest {

    @Mock
    private InterviewRepository interviewRepository;

    @InjectMocks
    private InMemoryStubInterviewPageQueryService interviewPageQueryService;

    @Mock
    private Interview interview1;

    @Mock
    private Interview interview2;

    @Mock
    private Interview interview3;

    private List<Interview> interviews;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        interviews = Arrays.asList(interview1, interview2, interview3);

        Mockito.when(interviewRepository.findAll()).thenReturn(interviews);
    }

    @Test
    public void testFindForReturnsCorrectPage() {
        int page = 0;
        int size = 2;
        InterviewPage result = interviewPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(interview1, result.content().get(0));
        assertEquals(interview2, result.content().get(1));
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(interviews.size(), result.totalElements());
    }

    @Test
    public void testFindForPageOutOfBoundsReturnsEmptyPage() {
        int page = 1;
        int size = 3;

        InterviewPage result = interviewPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(interviews.size(), result.totalElements());
    }
}