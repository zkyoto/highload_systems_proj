package ru.itmo.cs.app.interviewing.interview.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class ScheduleInterviewCommandHandler implements CommandHandler<ScheduleInterviewCommand> {
    private final InterviewRepository interviewRepository;

    @Override
    public void handle(ScheduleInterviewCommand command) {
        Interview interview = Interview.create(command.interviewerId, command.candidateId, command.scheduledTime);
        interviewRepository.save(interview);
    }
}
