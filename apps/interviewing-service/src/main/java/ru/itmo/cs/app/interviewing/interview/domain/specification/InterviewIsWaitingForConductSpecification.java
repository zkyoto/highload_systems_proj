package ru.itmo.cs.app.interviewing.interview.domain.specification;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.Schedule;

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
