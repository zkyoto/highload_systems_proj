package ru.ifmo.cs.interviews.domain.specification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.Schedule;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterviewIsFailedSpecificationTest {

    private Interview interview;

    private Schedule failedSchedule;
    private Schedule successfulSchedule;

    @BeforeEach
    void setUp() {
        interview = mock(Interview.class);
        failedSchedule = mock(Schedule.class);
        successfulSchedule = mock(Schedule.class);

        when(failedSchedule.isFailed()).thenReturn(true);
        when(successfulSchedule.isFailed()).thenReturn(false);
    }

    @Test
    void testIsSatisfiedBy_AllSchedulesFailed() {
        // Arrange
        when(interview.getSchedules()).thenReturn(Arrays.asList(failedSchedule, failedSchedule));

        // Act
        boolean result = InterviewIsFailedSpecification.isSatisfiedBy(interview);

        // Assert
        assertTrue(result, "Expected the specification to be satisfied because all schedules are failed.");
    }

    @Test
    void testIsSatisfiedBy_NotAllSchedulesFailed() {
        // Arrange
        when(interview.getSchedules()).thenReturn(Arrays.asList(failedSchedule, successfulSchedule));

        // Act
        boolean result = InterviewIsFailedSpecification.isSatisfiedBy(interview);

        // Assert
        assertFalse(result, "Expected the specification not to be satisfied because not all schedules are failed.");
    }

    @Test
    void testIsSatisfiedBy_NoSchedules() {
        // Arrange
        when(interview.getSchedules()).thenReturn(Collections.emptyList());

        // Act
        boolean result = InterviewIsFailedSpecification.isSatisfiedBy(interview);

        // Assert
        assertTrue(result, "Expected the specification to be satisfied because there are no schedules.");
    }
}