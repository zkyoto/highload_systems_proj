package ru.ifmo.cs.candidates.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class ChageStatusToProcessedCommand implements Command {
    CandidateId candidateId;
}
