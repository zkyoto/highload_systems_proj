package ru.ifmo.cs.feedbacks.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class RewriteFeedbackCommandHandler implements CommandHandler<RewriteFeedbackCommand> {
    private final FeedbackRepository feedbackRepository;

    @Override
    public void handle(RewriteFeedbackCommand command) {
        Feedback feedback = feedbackRepository.findById(command.feedbackId);
        feedback.rewrite(command.grade, command.comment);
        feedbackRepository.save(feedback);
    }
}
