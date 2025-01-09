package ru.ifmo.cs.interview_results.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.interview_results.domain.value.Verdict;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class CreateInterviewResultCommand implements Command {
    String feedbackId;
    Verdict verdict;
}
