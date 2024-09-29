package ru.itmo.cs.app.interviewing.interviewer.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class ActivateInterviewerCommandHandler implements CommandHandler<ActivateInterviewerCommand> {
    private final InterviewerRepository interviewerRepository;
    @Override
    public void handle(ActivateInterviewerCommand command) {
        Interviewer interviewer = interviewerRepository.findById(command.interviewerId);
        interviewer.activate();
        interviewerRepository.save(interviewer);
    }
}
