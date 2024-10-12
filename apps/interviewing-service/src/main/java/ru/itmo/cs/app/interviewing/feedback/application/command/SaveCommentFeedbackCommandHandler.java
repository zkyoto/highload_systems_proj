package ru.itmo.cs.app.interviewing.feedback.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class SaveCommentFeedbackCommandHandler implements CommandHandler<SaveCommentFeedbackCommand> {
    private final FeedbackRepository feedbackRepository;

    @Override
    public void handle(SaveCommentFeedbackCommand command) {
        Feedback feedback = feedbackRepository.findById(command.feedbackId);
        feedback.setComment(command.comment);
        feedbackRepository.save(feedback);
    }
}
