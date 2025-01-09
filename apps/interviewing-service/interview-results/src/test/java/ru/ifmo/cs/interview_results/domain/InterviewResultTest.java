package ru.ifmo.cs.interview_results.domain;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.cs.interview_results.domain.event.InterviewResultEvent;
import ru.ifmo.cs.interview_results.domain.value.Verdict;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterviewResultTest {
    private Verdict verdict;
    private InterviewResult interviewResult;

    @BeforeEach
    void setUp() {
        verdict = Verdict.HIRE;
        interviewResult = InterviewResult.create("feedbackId", verdict);
    }

    @Test
    void verifyThatAnInterviewResultCanBeCreatedSuccessfully() {
        assertTrue(interviewResult.getId() != null);
        assertTrue(interviewResult.getCreatedAt() != null);
        assertEquals("feedbackId", interviewResult.getFeedbackId());
        assertEquals(verdict, interviewResult.getVerdict());
        assertEquals(1, interviewResult.getEvents().size());
    }

    @Test
    void ensureThatEventsAreReleasedCorrectlyFromAnInterviewResult() {
        List<InterviewResultEvent> releasedEvents = interviewResult.releaseEvents();
        assertEquals(1, releasedEvents.size());
        assertEquals(interviewResult.getEvents().size(), 0);
    }

    @Test
    void validateRetrievalOfInterviewResultProperties() {
        assertEquals(interviewResult.getId(), interviewResult.getId());
        assertEquals(interviewResult.getCreatedAt(), interviewResult.getCreatedAt());
        assertEquals(interviewResult.getUpdatedAt(), interviewResult.getUpdatedAt());
        assertEquals("feedbackId", interviewResult.getFeedbackId());
        assertEquals(verdict, interviewResult.getVerdict());
    }

    @Test
    void checkThatTheCreatedTimestampIsSetCorrectly() {
        Instant createdAt = interviewResult.getCreatedAt();
        assertEquals(createdAt, interviewResult.getUpdatedAt());
    }

    @Test
    void verifyThatTheUpdatedTimestampEqualsCreatedAtCorrectly() {
        Instant initialUpdatedAt = interviewResult.getUpdatedAt();
        interviewResult = InterviewResult.create("feedbackId", Verdict.NO_HIRE);
        assertTrue(interviewResult.getUpdatedAt().isAfter(initialUpdatedAt));
    }

    @Test
    void verifyThatTheUpdatedTimestampChangesAfterModification() {
        Instant initialUpdatedAt = interviewResult.getUpdatedAt();
        assertEquals(interviewResult.getCreatedAt(), initialUpdatedAt);
    }

}
