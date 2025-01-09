package ru.ifmo.cs.interviews.application.command;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class ScheduleInterviewCommand implements Command {
    String interviewerId;
    String candidateId;
    Instant scheduledTime;
}

