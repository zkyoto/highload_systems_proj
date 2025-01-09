package ru.ifmo.cs.interviews.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

public class InterviewTest {
    private String interviewerId;
    private String candidateId;
    private Instant scheduledFor;
    private Interview interview;

    @BeforeEach
    void setUp() {
        interviewerId = "InterviewerId";
        candidateId = "CandidateId";
        scheduledFor = Instant.now().plusSeconds(3600);
        interview = Interview.create(interviewerId, candidateId, scheduledFor);
    }

    @Test
    void verifyThatAnInterviewCanBeScheduledSuccessfully() {
        assertNotNull(interview);
        assertEquals(scheduledFor, interview.getScheduledFor());
        assertFalse(interview.releaseEvents().isEmpty());
    }

    @Test
    void ensureThatAnInterviewCanBeRescheduledWhenItIsWaitingForConduct() {
        Instant newScheduledFor = Instant.now().plusSeconds(7200);
        interview.reschedule(newScheduledFor);
        assertEquals(newScheduledFor, interview.getScheduledFor());
        assertFalse(interview.releaseEvents().isEmpty());
    }

    @Test
    void validateThatAnExceptionIsThrownWhenTryingToRescheduleAnInterviewThatIsNotWaitingForConduct() {
        interview.cancel();
        Instant newScheduledFor = Instant.now().plusSeconds(7200);
        assertThrows(IllegalStateException.class, () -> interview.reschedule(newScheduledFor));
    }

    @Test
    void confirmThatAnInterviewCanBeCancelledSuccessfully() {
        interview.cancel();
        assertTrue(interview.getSchedules().stream().allMatch(Schedule::isCancelled));
        assertFalse(interview.releaseEvents().isEmpty());
    }

    @Test
    void ensureThatAnExceptionIsThrownWhenTryingToCancelAnAlreadyCancelledInterview() {
        interview.cancel();
        assertThrows(IllegalStateException.class, interview::cancel);
    }

}
