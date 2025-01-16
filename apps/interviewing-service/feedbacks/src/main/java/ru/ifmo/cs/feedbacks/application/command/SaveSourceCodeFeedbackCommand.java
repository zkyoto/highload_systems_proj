package ru.ifmo.cs.feedbacks.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.Grade;
import ru.ifmo.cs.feedbacks.domain.value.SourceCodeFileId;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class SaveSourceCodeFeedbackCommand implements Command {
    FeedbackId feedbackId;
    SourceCodeFileId sourceCodeFileId;
}
