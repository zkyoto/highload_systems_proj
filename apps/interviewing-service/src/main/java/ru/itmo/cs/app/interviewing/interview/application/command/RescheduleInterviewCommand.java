package ru.itmo.cs.app.interviewing.interview.application.command;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class RescheduleInterviewCommand implements Command {
    InterviewId interviewId;
    Instant newScheduledTime;
}
