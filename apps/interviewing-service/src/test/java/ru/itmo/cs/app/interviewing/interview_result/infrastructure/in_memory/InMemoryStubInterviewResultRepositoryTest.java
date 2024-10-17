package ru.itmo.cs.app.interviewing.interview_result.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultCreatedEvent;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryStubInterviewResultRepositoryTest {

    private InMemoryStubInterviewResultRepository repository;

    @Mock
    private StoredDomainEventRepository storedDomainEventRepository;

    @Mock
    private InterviewResult interviewResult;

    @Mock
    private InterviewResultCreatedEvent interviewResultCreatedEvent;

    @Mock
    private InterviewResultId interviewResultId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new InMemoryStubInterviewResultRepository(storedDomainEventRepository);

        when(interviewResult.getId()).thenReturn(interviewResultId);
        when(interviewResult.releaseEvents()).thenReturn(List.of(interviewResultCreatedEvent));
    }

    @Test
    void testSaveInsertsNewInterviewResult() {
        repository.save(interviewResult);

        InterviewResult foundResult = repository.findById(interviewResultId);
        assertEquals(interviewResult, foundResult);

        verify(storedDomainEventRepository, times(1)).save(any(StoredDomainEvent.class));
    }

    @Test
    void testSaveUpdatesExistingInterviewResult() {
        repository.save(interviewResult);
        repository.save(interviewResult); // Saving again to simulate an update

        InterviewResult foundResult = repository.findById(interviewResultId);
        assertEquals(interviewResult, foundResult);

        verify(storedDomainEventRepository, times(2)).save(any(StoredDomainEvent.class));
    }

    @Test
    void testFindAllReturnsAllInterviewResults() {
        repository.save(interviewResult);

        List<InterviewResult> results = repository.findAll();
        assertEquals(1, results.size());
        assertEquals(interviewResult, results.get(0));
    }

    @Test
    void testFindByIdThrowsExceptionIfNotFound() {
        assertThrows(RuntimeException.class, () -> repository.findById(interviewResultId));
    }
}