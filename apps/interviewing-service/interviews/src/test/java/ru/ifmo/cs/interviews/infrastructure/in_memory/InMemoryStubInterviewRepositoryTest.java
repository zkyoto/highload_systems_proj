package ru.ifmo.cs.interviews.infrastructure.in_memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.event.InterviewScheduledEvent;
import ru.ifmo.cs.interviews.domain.value.InterviewId;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InMemoryStubInterviewRepositoryTest {

    @Mock
    private StoredDomainEventRepository storedDomainEventRepository;

    @Mock
    private Interview interview;

    @Mock
    private InterviewId interviewId;

    @InjectMocks
    private InMemoryStubInterviewRepository interviewRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(interview.getId()).thenReturn(interviewId);
        when(interview.releaseEvents()).thenReturn(List.of(Mockito.mock(InterviewScheduledEvent.class)));

    }

    @Test
    public void testFindById() {
        interviewRepository.save(interview);

        Interview foundInterview = interviewRepository.findById(interviewId);
        assertEquals(interview, foundInterview);
    }

    @Test
    public void testFindByIdThrowsExceptionIfNotFound() {
        assertThrows(Exception.class, () -> {
            interviewRepository.findById(interviewId);
        });
    }

    @Test
    public void testFindAll() {
        interviewRepository.save(interview);

        List<Interview> interviews = interviewRepository.findAll();
        assertEquals(1, interviews.size());
        assertEquals(interview, interviews.get(0));
    }

    @Test
    public void testSaveNewInterview() {
        InterviewScheduledEvent scheduledEvent = mock(InterviewScheduledEvent.class);
        when(interview.releaseEvents()).thenReturn(List.of(scheduledEvent));

        interviewRepository.save(interview);

        verify(storedDomainEventRepository, times(1)).save(any(StoredDomainEvent.class));
    }

    @Test
    public void testSaveExistingInterviewUpdatesIt() {
        interviewRepository.save(interview);

        int initialSize = interviewRepository.findAll().size();
        when(interview.releaseEvents()).thenReturn(Collections.emptyList());
        
        interviewRepository.save(interview);

        assertEquals(initialSize, interviewRepository.findAll().size());
    }

    @Test
    public void testUpdateThrowsExceptionIfInterviewNotFound() {
        when(interview.releaseEvents()).thenReturn(Collections.emptyList());

        assertThrows(IllegalStateException.class, () -> {
            interviewRepository.save(interview);
        });
    }
}