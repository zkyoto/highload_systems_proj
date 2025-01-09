package ru.ifmo.cs.interviews.domain.specification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.Schedule;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InterviewIsWaitingForConductSpecificationTest {

    @Mock
    private Interview interview;

    @Mock
    private Schedule schedule1;

    @Mock
    private Schedule schedule2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsSatisfiedByReturnsTrueWhenThereIsAFutureSchedule() {
        when(schedule1.isActual()).thenReturn(true);
        when(schedule1.getScheduledFor()).thenReturn(Instant.now().plusSeconds(3600));

        when(interview.getSchedules()).thenReturn(List.of(schedule1));

        assertTrue(InterviewIsWaitingForConductSpecification.isSatisfiedBy(interview),
                "Expected true when there is a future schedule");
    }

    @Test
    void testIsSatisfiedByReturnsFalseWhenAllSchedulesInPast() {
        when(schedule1.isActual()).thenReturn(true);
        when(schedule1.getScheduledFor()).thenReturn(Instant.now().minusSeconds(3600));

        when(interview.getSchedules()).thenReturn(List.of(schedule1));

        assertFalse(InterviewIsWaitingForConductSpecification.isSatisfiedBy(interview),
                "Expected false when all schedules are in the past");
    }

    @Test
    void testIsSatisfiedByReturnsFalseWhenNoActualSchedules() {
        when(schedule1.isActual()).thenReturn(false);

        when(interview.getSchedules()).thenReturn(List.of(schedule1));

        assertFalse(InterviewIsWaitingForConductSpecification.isSatisfiedBy(interview),
                "Expected false when there are no actual schedules");
    }

    @Test
    void testIsSatisfiedByReturnsFalseWhenNoSchedules() {
        when(interview.getSchedules()).thenReturn(List.of());

        assertFalse(InterviewIsWaitingForConductSpecification.isSatisfiedBy(interview),
                "Expected false when there are no schedules");
    }

    @Test
    void testIsSatisfiedByReturnsTrueWhenMultipleSchedulesAndOneIsUpcoming() {
        when(schedule1.isActual()).thenReturn(true);
        when(schedule1.getScheduledFor()).thenReturn(Instant.now().plusSeconds(3600));

        when(schedule2.isActual()).thenReturn(true);
        when(schedule2.getScheduledFor()).thenReturn(Instant.now().plusSeconds(3600));

        when(interview.getSchedules()).thenReturn(List.of(schedule1, schedule2));

        assertTrue(InterviewIsWaitingForConductSpecification.isSatisfiedBy(interview),
                "Expected true when there is at least one upcoming schedule");
    }
}