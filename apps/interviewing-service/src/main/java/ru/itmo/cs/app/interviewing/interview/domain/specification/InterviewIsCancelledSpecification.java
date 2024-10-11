package ru.itmo.cs.app.interviewing.interview.domain.specification;

import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.Schedule;

public class InterviewIsCancelledSpecification {

    public boolean isSatisfiedBy(Interview interview) {
        return interview.getSchedules().stream().allMatch(Schedule::isCancelled);
    }

}
