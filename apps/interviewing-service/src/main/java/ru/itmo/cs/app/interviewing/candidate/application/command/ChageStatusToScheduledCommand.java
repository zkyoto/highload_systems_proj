package ru.itmo.cs.app.interviewing.candidate.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class ChageStatusToScheduledCommand implements Command {
    CandidateId candidateId;
}
