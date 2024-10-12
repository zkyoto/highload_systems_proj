package ru.itmo.cs.app.interviewing.feedback.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class SubmitFeedbackCommand implements Command {
    FeedbackId feedbackId;
}
