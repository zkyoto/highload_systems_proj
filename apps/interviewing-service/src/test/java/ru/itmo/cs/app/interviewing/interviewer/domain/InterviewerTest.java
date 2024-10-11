package ru.itmo.cs.app.interviewing.interviewer.domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerActivatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerCreatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerDemotedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerStatus;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class InterviewerTest {
    private Interviewer interviewer;

    @BeforeEach
    void setUp() {
        interviewer = Interviewer.create(UserId.of(1488), Name.of("Z V"));
    }

    @Test
    void create_ShouldInitializeInterviewerWithCorrectValues() {
        Assertions.assertThat(interviewer.getId()).isNotNull();
        Assertions.assertThat(interviewer.getCreatedAt()).isNotNull();
        Assertions.assertThat(interviewer.getUpdatedAt()).isNotNull();
        assertTrue(interviewer.getCreatedAt().isBefore(Instant.now()));
        assertTrue(interviewer.getUpdatedAt().isBefore(Instant.now()));
    }

    @Test
    void create_ShouldAddInterviewerCreatedEvent() {
        List<InterviewerEvent> events = interviewer.releaseEvents();
        Assertions.assertThat(events).hasSize(1);
        assertTrue(events.get(0) instanceof InterviewerCreatedEvent);
    }

    @Test
    void releaseEvents_ShouldReturnAllPendingEvents() {
        List<InterviewerEvent> events = interviewer.releaseEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof InterviewerCreatedEvent);
    }

    @Test
    void releaseEvents_ShouldResetEventsList() {
        assertEquals(1, interviewer.releaseEvents().size());
        assertTrue(interviewer.releaseEvents().isEmpty());
    }

    @Test
    public void testActivateWhenPending() {
        preparePending();

        interviewer.activate();
        Instant oldUpdatedAt = interviewer.getUpdatedAt();
        assertEquals(InterviewerStatus.ACTIVE, interviewer.getInterviewerStatus());
        assertTrue(oldUpdatedAt.isBefore(Instant.now()));
        List<InterviewerEvent> events = interviewer.releaseEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof InterviewerActivatedEvent);
    }

    @Test
    public void testActivateWhenDemoted() {
        prepareDemoted();

        interviewer.activate();
        Instant oldUpdatedAt = interviewer.getUpdatedAt();

        assertEquals(InterviewerStatus.ACTIVE, interviewer.getInterviewerStatus());
        assertTrue(oldUpdatedAt.isBefore(Instant.now()));
        List<InterviewerEvent> events = interviewer.releaseEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof InterviewerActivatedEvent);
    }

    @Test
    public void testActivateThrowsException() {
        prepareActivated();
        assertThrows(IllegalStateException.class, interviewer::activate);
    }

    @Test
    public void testDemoteWhenActive() {
        prepareActivated();

        interviewer.demote();
        Instant oldUpdatedAt = interviewer.getUpdatedAt();

        assertEquals(InterviewerStatus.DEMOTED, interviewer.getInterviewerStatus());
        assertTrue(oldUpdatedAt.isBefore(Instant.now()));
        List<InterviewerEvent> events = interviewer.releaseEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof InterviewerDemotedEvent);
    }

    @Test
    public void testDemoteThrowsException() {
        prepareDemoted();
        assertThrows(IllegalStateException.class, interviewer::demote);
    }

    @Test
    public void testDemoteThrowsExceptionWhenStatusIsPendingActivation() {
        assertThrows(IllegalStateException.class, interviewer::demote);
    }

    private void prepareDemoted() {
        interviewer.activate();
        interviewer.demote();
        interviewer.releaseEvents();
    }

    private void prepareActivated() {
        interviewer.activate();
        interviewer.releaseEvents();
    }

    private void preparePending() {
        interviewer.releaseEvents();
    }

}