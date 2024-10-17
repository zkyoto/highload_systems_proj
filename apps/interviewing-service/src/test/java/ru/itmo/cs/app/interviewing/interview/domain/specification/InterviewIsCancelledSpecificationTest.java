package ru.itmo.cs.app.interviewing.interview.domain.specification;

import org.junit.jupiter.api.Test;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.Schedule;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InterviewIsCancelledSpecificationTest {

    @Test
    void testIsSatisfiedByReturnsTrueWhenAllSchedulesAreCancelled() {
        Schedule schedule1 = mock(Schedule.class);
        Schedule schedule2 = mock(Schedule.class);

        when(schedule1.isCancelled()).thenReturn(true);
        when(schedule2.isCancelled()).thenReturn(true);

        Interview interview = mock(Interview.class);
        when(interview.getSchedules()).thenReturn(List.of(schedule1, schedule2));

        assertTrue(InterviewIsCancelledSpecification.isSatisfiedBy(interview),
                "Expected true when all schedules are cancelled");
    }

    @Test
    void testIsSatisfiedByReturnsFalseWhenSomeSchedulesAreNotCancelled() {
        Schedule schedule1 = mock(Schedule.class);
        Schedule schedule2 = mock(Schedule.class);

        when(schedule1.isCancelled()).thenReturn(true);
        when(schedule2.isCancelled()).thenReturn(false);

        Interview interview = mock(Interview.class);
        when(interview.getSchedules()).thenReturn(List.of(schedule1, schedule2));

        assertFalse(InterviewIsCancelledSpecification.isSatisfiedBy(interview),
                "Expected false when not all schedules are cancelled");
    }

    @Test
    void testIsSatisfiedByReturnsTrueWhenNoSchedules() {
        Interview interview = mock(Interview.class);
        when(interview.getSchedules()).thenReturn(List.of());

        assertTrue(InterviewIsCancelledSpecification.isSatisfiedBy(interview),
                "Expected true when there are no schedules (trivially satisfied)");
    }
}