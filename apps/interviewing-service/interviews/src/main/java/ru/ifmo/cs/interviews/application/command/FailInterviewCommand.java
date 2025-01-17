package ru.ifmo.cs.interviews.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.interviews.domain.value.InterviewId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class FailInterviewCommand implements Command {
    InterviewId interviewId;
}
