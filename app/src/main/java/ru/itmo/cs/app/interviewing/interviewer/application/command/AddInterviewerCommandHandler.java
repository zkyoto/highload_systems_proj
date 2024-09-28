package ru.itmo.cs.app.interviewing.interviewer.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
import ru.itmo.cs.common.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class AddInterviewerCommandHandler implements CommandHandler<AddInterviewerCommand> {
    private final InterviewerRepository repository;
    @Override
    public void handle(AddInterviewerCommand command) {
        Interviewer interviewer = Interviewer.create();
        repository.save(interviewer);
    }
}
