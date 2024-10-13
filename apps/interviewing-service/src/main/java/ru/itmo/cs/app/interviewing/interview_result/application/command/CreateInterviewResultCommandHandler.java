package ru.itmo.cs.app.interviewing.interview_result.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;
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
