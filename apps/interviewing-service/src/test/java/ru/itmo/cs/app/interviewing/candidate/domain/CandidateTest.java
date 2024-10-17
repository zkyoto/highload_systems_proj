package ru.itmo.cs.app.interviewing.candidate.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.And;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CandidateTest {
    private Name name;
    private Candidate candidate;

    @BeforeEach
    void setUp() {
        name = new Name("John", "Doe", "John Doe");
        candidate = Candidate.create(name);
    }

    @Test
    void verifyThatACandidateCanBeCreatedSuccessfully() {
        assertNotNull(candidate.getId());
        assertEquals(CandidateStatus.WAITING_FOR_APPOINTMENT_AN_INTERVIEW, candidate.getStatus());
        assertNotNull(candidate.getCreatedAt());
        assertFalse(candidate.getEvents().isEmpty());
    }

    @Test
    void ensureThatACandidatesStatusCanBeChangedToScheduled() {
        candidate.changeStatusToScheduled();
        assertEquals(CandidateStatus.WAITING_FOR_INTERVIEW, candidate.getStatus());
        assertNotNull(candidate.getUpdatedAt());
        assertFalse(candidate.getEvents().isEmpty());
    }

    @Test
    void validateThatAnExceptionIsThrownWhenChangingStatusFromAnInvalidState() {
        candidate.changeStatusToScheduled();
        assertThrows(IllegalStateException.class, candidate::changeStatusToScheduled);
        assertEquals(CandidateStatus.WAITING_FOR_INTERVIEW, candidate.getStatus());
    }

    @Test
    void checkThatEventsCanBeReleasedAfterAStatusChange() {
        candidate.changeStatusToScheduled();
        var releasedEvents = candidate.releaseEvents();
        assertFalse(releasedEvents.isEmpty());
        assertTrue(candidate.getEvents().isEmpty());
    }

    @Test
    void confirmThatTheCandidatesDetailsCanBeRetrievedCorrectly() {
        assertEquals(candidate.getId(), candidate.getId());
        assertEquals(name, candidate.getName());
        assertNotNull(candidate.getCreatedAt());
        assertNotNull(candidate.getUpdatedAt());
        assertEquals(CandidateStatus.WAITING_FOR_APPOINTMENT_AN_INTERVIEW, candidate.getStatus());
    }

}
