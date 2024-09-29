package ru.itmo.cs.app.interviewing.interviewer.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
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
