package ru.ifmo.cs.interviews.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class FailInterviewCommandHandler implements CommandHandler<FailInterviewCommand> {
    private final InterviewRepository interviewRepository;

    @Override
    public void handle(FailInterviewCommand command) {
        Interview interview = interviewRepository.findById(command.interviewId);
        interview.fail();
        interviewRepository.save(interview);
    }
}
