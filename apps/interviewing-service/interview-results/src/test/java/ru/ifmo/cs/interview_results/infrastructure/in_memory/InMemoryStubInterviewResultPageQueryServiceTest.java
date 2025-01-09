package ru.ifmo.cs.interview_results.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.interview_results.application.query.dto.InterviewResultPage;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStubInterviewResultPageQueryServiceTest {

    @Mock
    private InterviewResultRepository interviewResultRepository;

    @InjectMocks
    private InMemoryStubInterviewResultPageQueryService interviewResultPageQueryService;

    @Mock
    private InterviewResult interviewResult1;

    @Mock
    private InterviewResult interviewResult2;

    @Mock
    private InterviewResult interviewResult3;

    private List<InterviewResult> interviewResults;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        interviewResults = Arrays.asList(interviewResult1, interviewResult2, interviewResult3);

        Mockito.when(interviewResultRepository.findAll()).thenReturn(interviewResults);
    }

    @Test
    public void testFindForReturnsCorrectPage() {
        int page = 0;
        int size = 2;
        InterviewResultPage result = interviewResultPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(interviewResult1, result.content().get(0));
        assertEquals(interviewResult2, result.content().get(1));
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(interviewResults.size(), result.totalElements());
    }

    @Test
    public void testFindForPageOutOfBoundsReturnsEmptyPage() {
        int page = 1;
        int size = 3;

        InterviewResultPage result = interviewResultPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(interviewResults.size(), result.totalElements());
    }
}