package ru.itmo.cs.app.interviewing.interviewer.infrastructure.in_memory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.app.interviewing.configuration.InterviewingServiceConfiguration;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;


@SpringBootTest
public class InMemoryStubInterviewerRepositoryTest {
    @Autowired
    private InMemoryStubInterviewerRepository repository;
    private InterviewerId dummyId;
    private Interviewer dummyInterviewer;

    @BeforeEach
    void setUp() {
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
        assertThrows(IllegalArgumentException.class, () -> repository.findById(dummyId));
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