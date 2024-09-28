package ru.itmo.cs.app.interviewing.interviewer.domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerCreatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerEvent;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class InterviewerTest {
    private Interviewer interviewer;

    @BeforeEach
    void setUp() {
        interviewer = Interviewer.create();
    }

    @Test
    void create_ShouldInitializeInterviewerWithCorrectValues() {
        Assertions.assertThat(interviewer.getInterviewerId()).isNotNull();
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
        interviewer = Interviewer.create();
        List<InterviewerEvent> events = interviewer.releaseEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof InterviewerCreatedEvent);
    }

    @Test
    void releaseEvents_ShouldResetEventsList() {
        assertEquals(1, interviewer.releaseEvents().size());
        assertTrue(interviewer.releaseEvents().isEmpty());
    }
}