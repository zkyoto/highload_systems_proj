package ru.ifmo.cs.interview_results.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interview_results.application.command.CreateInterviewResultCommand;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class CreateInterviewResultCommandHandler implements CommandHandler<CreateInterviewResultCommand> {
    private final InterviewResultRepository interviewResultRepository;

    @Override
    public void handle(CreateInterviewResultCommand command) {
        InterviewResult interviewResult = InterviewResult.create(command.feedbackId, command.verdict);
        interviewResultRepository.save(interviewResult);
    }

}
