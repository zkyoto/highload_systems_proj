package ru.itmo.cs.app.interviewing.feedback.application.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.command_bus.CommandHandler;

@Service
@AllArgsConstructor
public class SaveGradeFeedbackCommandHandler implements CommandHandler<SaveGradeFeedbackCommand> {
    private final FeedbackRepository feedbackRepository;

    @Override
    public void handle(SaveGradeFeedbackCommand command) {
        Feedback feedback = feedbackRepository.findById(command.feedbackId);
        feedback.setGrade(command.grade);
        feedbackRepository.save(feedback);
    }
}
