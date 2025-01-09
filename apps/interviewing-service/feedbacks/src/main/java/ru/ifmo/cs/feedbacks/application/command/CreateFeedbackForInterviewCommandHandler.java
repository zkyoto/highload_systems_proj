package ru.ifmo.cs.feedbacks.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class CreateFeedbackForInterviewCommandHandler implements CommandHandler<CreateFeedbackForInterviewCommand> {
    private final FeedbackRepository feedbackRepository;

    @Override
    public void handle(CreateFeedbackForInterviewCommand command) {
        Feedback feedback = Feedback.create(command.interviewId);
        feedbackRepository.save(feedback);
    }
}
