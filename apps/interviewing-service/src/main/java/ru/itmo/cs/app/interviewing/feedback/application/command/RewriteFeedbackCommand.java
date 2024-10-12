package ru.itmo.cs.app.interviewing.feedback.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class RewriteFeedbackCommand implements Command {
    FeedbackId feedbackId;
    Grade grade;
    Comment comment;
}
