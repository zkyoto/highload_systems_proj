package ru.ifmo.cs.interviews.domain.specification;

import java.time.Instant;

import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.Schedule;

public class InterviewIsWaitingForConductSpecification {

    public static boolean isSatisfiedBy(Interview interview) {
        return interview.getSchedules().stream()
                .filter(Schedule::isActual)
                .findAny()
                .map(Schedule::getScheduledFor)
                .map(scheduledFor -> scheduledFor.isAfter(Instant.now()))
                .orElse(false);
    }

}
