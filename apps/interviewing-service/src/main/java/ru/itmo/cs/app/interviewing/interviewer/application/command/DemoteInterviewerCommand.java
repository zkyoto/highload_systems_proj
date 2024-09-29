package ru.itmo.cs.app.interviewing.interviewer.application.command;

import lombok.AllArgsConstructor;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
public class DemoteInterviewerCommand implements Command {
    InterviewerId interviewerId;
}
