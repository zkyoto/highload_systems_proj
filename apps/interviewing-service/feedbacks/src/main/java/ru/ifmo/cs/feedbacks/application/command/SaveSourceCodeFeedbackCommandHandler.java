package ru.ifmo.cs.feedbacks.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class SaveSourceCodeFeedbackCommandHandler implements CommandHandler<SaveSourceCodeFeedbackCommand> {
    private final FeedbackRepository feedbackRepository;

    @Override
    public void handle(SaveSourceCodeFeedbackCommand command) {
        Feedback feedback = feedbackRepository.findById(command.feedbackId);
        feedback.setSourceCodeFileId(command.sourceCodeFileId);
        feedbackRepository.save(feedback);
    }
}
