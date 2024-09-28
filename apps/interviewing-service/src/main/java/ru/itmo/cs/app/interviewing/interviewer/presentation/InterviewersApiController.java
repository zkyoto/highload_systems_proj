package ru.itmo.cs.app.interviewing.interviewer.presentation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.interviewer.application.command.AddInterviewerCommand;
import ru.itmo.cs.command_bus.CommandBus;

@AllArgsConstructor
@RestController
public class InterviewersApiController {
    private final CommandBus commandBus;

    @PostMapping("/api/v1/interviewers/add")
    public ResponseEntity createInterviewer() {
        commandBus.submit(new AddInterviewerCommand());
        return ResponseEntity.ok().build();
    }
}
