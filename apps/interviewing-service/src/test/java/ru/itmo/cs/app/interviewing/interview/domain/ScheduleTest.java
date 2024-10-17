package ru.itmo.cs.app.interviewing.interview.domain;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.And;
import ru.itmo.cs.app.interviewing.interview.domain.value.ScheduleStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduleTest {
    private Schedule schedule;
    private Instant scheduledFor;

    @BeforeEach
    void setUp() {
        scheduledFor = Instant.now().plusSeconds(3600);
        schedule = Schedule.create(scheduledFor);
    }

    @Test
    void verifyThatANewlyCreatedScheduleIsMarkedAsActual() {
        assertEquals(ScheduleStatus.ACTUAL, schedule.getStatus());
        assertEquals(schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    @Test
    void ensureThatAScheduleCanBeCancelled() {
        schedule.cancel();
        assertEquals(ScheduleStatus.CANCELLED, schedule.getStatus());
        assertNotEquals(schedule.getUpdatedAt(), schedule.getCreatedAt());
    }

    @Test
    void checkIfAScheduleIsActualAfterCreation() {
        assertTrue(schedule.isActual());
    }

    @Test
    void verifyThatACancelledScheduleIsNotActual() {
        schedule.cancel();
        assertFalse(schedule.isActual());
        assertTrue(schedule.isCancelled());
    }

    @Test
    void validateTheUpdatedTimeAfterCancellingASchedule() {
        Instant beforeCancel = schedule.getUpdatedAt();
        schedule.cancel();
        assertNotEquals(beforeCancel, schedule.getUpdatedAt());
        assertEquals(schedule.getCreatedAt(), schedule.getCreatedAt());
    }

}
