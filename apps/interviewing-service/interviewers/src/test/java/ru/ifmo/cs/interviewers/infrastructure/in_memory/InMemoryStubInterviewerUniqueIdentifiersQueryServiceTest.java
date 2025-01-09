package ru.ifmo.cs.interviewers.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.InterviewerRepository;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryStubInterviewerUniqueIdentifiersQueryServiceTest {

    private InMemoryStubInterviewerUniqueIdentifiersQueryService queryService;

    @Mock
    private InterviewerRepository interviewerRepository;

    @Mock
    private Interviewer interviewer;

    @Mock
    private UserId userId;

    @Mock
    private InterviewerId interviewerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        queryService = new InMemoryStubInterviewerUniqueIdentifiersQueryService(interviewerRepository);

        when(interviewer.getUserId()).thenReturn(userId);
        when(interviewer.getId()).thenReturn(interviewerId);
        when(interviewerRepository.findAll()).thenReturn(List.of(interviewer));
    }

    @Test
    void testFindByUserIdReturnsDto() {
        InterviewerUniqueIdentifiersDto dto = queryService.findBy(userId);
        assertNotNull(dto);
        verify(interviewerRepository, times(1)).findAll();
    }

    @Test
    void testFindByInterviewerIdReturnsDto() {
        InterviewerUniqueIdentifiersDto dto = queryService.findBy(interviewerId);
        assertNotNull(dto);
        verify(interviewerRepository, times(1)).findAll();
    }

    @Test
    void testFindByUserIdThrowsExceptionIfNotFound() {
        when(interviewerRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(Exception.class, () -> queryService.findBy(userId));
    }

    @Test
    void testFindByInterviewerIdThrowsExceptionIfNotFound() {
        when(interviewerRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(Exception.class, () -> queryService.findBy(interviewerId));
    }
}