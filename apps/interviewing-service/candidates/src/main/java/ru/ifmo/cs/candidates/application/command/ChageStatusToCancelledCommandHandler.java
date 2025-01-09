package ru.ifmo.cs.candidates.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class ChageStatusToCancelledCommandHandler implements CommandHandler<ChageStatusToCancelledCommand> {
    private final CandidateRepository candidateRepository;

    @Override
    public void handle(ChageStatusToCancelledCommand command) {
        Candidate candidate = candidateRepository.findById(command.candidateId);
        candidate.changeStatusToCancelled();
        candidateRepository.save(candidate);
    }

}
