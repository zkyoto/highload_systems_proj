package ru.ifmo.cs.candidates.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class AddCandidateCommandHandler implements CommandHandler<AddCandidateCommand> {
    private final CandidateRepository candidateRepository;
    @Override
    public void handle(AddCandidateCommand command) {
        Candidate candidate = Candidate.create(command.candidateName);
        candidateRepository.save(candidate);
    }
}
