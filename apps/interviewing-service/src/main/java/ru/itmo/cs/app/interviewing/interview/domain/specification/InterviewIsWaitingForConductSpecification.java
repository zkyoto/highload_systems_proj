package ru.itmo.cs.app.interviewing.interview.domain.specification;

import java.time.Instant;

import org.springframework.stereotype.Component;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.Schedule;

@Component
public class InterviewIsWaitingForConductSpecification {

    public boolean isSatisfiedBy(Interview interview) {
        return interview.getSchedules().stream()
                .filter(Schedule::isActual)
                .findAny()
                .map(Schedule::getScheduledFor)
                .map(scheduledFor -> scheduledFor.isAfter(Instant.now()))
                .orElse(false);
    }

}
