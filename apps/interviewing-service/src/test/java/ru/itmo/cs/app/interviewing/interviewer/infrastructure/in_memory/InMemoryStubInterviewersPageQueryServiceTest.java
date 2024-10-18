package ru.itmo.cs.app.interviewing.interviewer.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.interviewer.application.query.dto.InterviewersPage;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStubInterviewersPageQueryServiceTest {

    @Mock
    private InterviewerRepository interviewerRepository;

    @InjectMocks
    private InMemoryStubInterviewersPageQueryService interviewersPageQueryService;

    private List<Interviewer> interviewers;

    @Mock
    private Interviewer interviewer1;

    @Mock
    private Interviewer interviewer2;

    @Mock
    private Interviewer interviewer3;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        interviewers = Arrays.asList(interviewer1, interviewer2, interviewer3);

        Mockito.when(interviewer1.getName()).thenReturn(Name.of("John Doe"));
        Mockito.when(interviewer2.getName()).thenReturn(Name.of("Jane Doe"));

        Mockito.when(interviewerRepository.findAll()).thenReturn(interviewers);
    }

    @Test
    public void testFindForReturnsCorrectPage() {
        int page = 0;
        int size = 2;
        InterviewersPage result = interviewersPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(Name.of("John Doe"), result.content().get(0).getName());
        assertEquals(Name.of("Jane Doe"), result.content().get(1).getName());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(interviewers.size(), result.totalElements());
    }

    @Test
    public void testFindForPageOutOfBoundsReturnsEmptyPage() {
        int page = 1;
        int size = 3;

        InterviewersPage result = interviewersPageQueryService.findFor(page, size);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(interviewers.size(), result.totalElements());
    }
}