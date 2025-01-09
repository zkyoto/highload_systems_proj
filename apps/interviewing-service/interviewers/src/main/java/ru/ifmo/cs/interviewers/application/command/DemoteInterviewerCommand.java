package ru.ifmo.cs.interviewers.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class DemoteInterviewerCommand implements Command {
    InterviewerId interviewerId;
}
