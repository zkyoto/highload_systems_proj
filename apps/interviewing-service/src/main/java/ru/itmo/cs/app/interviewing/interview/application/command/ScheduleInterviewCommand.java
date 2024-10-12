package ru.itmo.cs.app.interviewing.interview.application.command;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;
import ru.itmo.cs.command_bus.Command;

@FieldDefaults(makeFinal = true)
@AllArgsConstructor
public class ScheduleInterviewCommand implements Command {
    InterviewerId interviewerId;
    CandidateId candidateId;
    Instant scheduledTime;
}
