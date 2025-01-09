package ru.ifmo.cs.interviewers.infrastructure.in_memory;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;


public class InMemoryStubInterviewerRepositoryTest {
    @Mock
    private StoredDomainEventRepository storedDomainEventRepository;
    private InMemoryStubInterviewerRepository repository;
    private InterviewerId dummyId;
    private Interviewer dummyInterviewer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new InMemoryStubInterviewerRepository(storedDomainEventRepository);
        dummyInterviewer = Interviewer.create(UserId.of(1488), Name.of("Z V"));
        dummyId = dummyInterviewer.getId();
    }

    @Test
    void findById_ShouldReturnInterviewer_WhenInterviewerExists() {
        repository.save(dummyInterviewer);
        Interviewer foundInterviewer = repository.findById(dummyId);
        assertEquals(dummyInterviewer, foundInterviewer);
    }

    @Test
    void findById_ShouldThrowException_WhenInterviewerDoesNotExist() {
        assertThrows(NoSuchElementException.class, () -> repository.findById(dummyId));
    }

    @Test
    void save_ShouldInsertNewInterviewer_WhenInterviewerIsNew() {
        repository.save(dummyInterviewer);

        Interviewer foundInterviewer = repository.findById(dummyId);
        assertEquals(dummyInterviewer, foundInterviewer);
    }

    @Test
    void save_ShouldUpdateExistingInterviewer_WhenInterviewerExists() {
        repository.save(dummyInterviewer);

        Interviewer updatedInterviewer = repository.findById(dummyId);
        repository.save(updatedInterviewer);

        Interviewer foundInterviewer = repository.findById(dummyId);
        assertEquals(updatedInterviewer, foundInterviewer);
    }

    @Test
    void save_ShouldThrowException_WhenUpdatingNonExistingInterviewer() {
        dummyInterviewer.releaseEvents();
        assertThrows(IllegalStateException.class, () -> repository.save(dummyInterviewer));
    }
}