package ru.ifmo.cs.interviewers.application.command;


import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class AddInterviewerCommand implements Command {
    UserId userId;
}
