package ru.itmo.cs.app.interviewing.interviewer.application.command;


import lombok.AllArgsConstructor;
import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
public class AddInterviewerCommand implements Command {
    UserId userId;
}
