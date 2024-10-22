package ru.itmo.cs.app.interviewing.candidate.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class ChageStatusToScheduledCommandHandler implements CommandHandler<ChageStatusToScheduledCommand> {
    private final CandidateRepository candidateRepository;

    @Override
    public void handle(ChageStatusToScheduledCommand command) {
        Candidate candidate = candidateRepository.findById(command.candidateId);
        candidate.changeStatusToScheduled();
        candidateRepository.save(candidate);
    }

}
