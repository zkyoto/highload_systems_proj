package ru.itmo.cs.app.interviewing.interview_result.application.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.Verdict;
import ru.itmo.cs.command_bus.Command;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class CreateInterviewResultCommand implements Command {
    FeedbackId feedbackId;
    Verdict verdict;
}
