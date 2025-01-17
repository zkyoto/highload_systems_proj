package ru.ifmo.cs.interviews.domain.specification;

import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.Schedule;

public class InterviewIsFailedSpecification {

    public static boolean isSatisfiedBy(Interview interview) {
        return interview.getSchedules().stream().allMatch(Schedule::isFailed);
    }

}
