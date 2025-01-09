package ru.ifmo.cs.interviews.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class CancelInterviewCommandHandler implements CommandHandler<CancelInterviewCommand> {
    private final InterviewRepository interviewRepository;

    @Override
    public void handle(CancelInterviewCommand command) {
        Interview interview = interviewRepository.findById(command.interviewId);
        interview.cancel();
        interviewRepository.save(interview);
    }
}
