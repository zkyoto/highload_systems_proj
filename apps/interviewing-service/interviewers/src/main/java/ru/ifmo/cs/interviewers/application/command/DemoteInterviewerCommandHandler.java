package ru.ifmo.cs.interviewers.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.InterviewerRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class DemoteInterviewerCommandHandler implements CommandHandler<DemoteInterviewerCommand> {
    private final InterviewerRepository interviewerRepository;
    @Override
    public void handle(DemoteInterviewerCommand command) {
        Interviewer interviewer = interviewerRepository.findById(command.interviewerId);
        interviewer.demote();
        interviewerRepository.save(interviewer);
    }
}
