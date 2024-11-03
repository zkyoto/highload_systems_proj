package ru.itmo.cs.app.interviewing.interviewer.presentation.controller;

import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.interviewer.application.command.ActivateInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.command.AddInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.command.DemoteInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.query.InterviewersPageQueryService;
import ru.itmo.cs.app.interviewing.interviewer.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.itmo.cs.app.interviewing.interviewer.application.query.InterviewerUniqueIdentifiersQueryService;
import ru.itmo.cs.app.interviewing.interviewer.application.query.dto.InterviewersPage;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.request.AddInterviewerRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.request.ActivateInterviewerRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.request.DemoteInterviewerRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.response.GetAllInterviewersResponseBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.response.GetInterviewerResponseBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.response.InterviewerResponseDto;
import ru.itmo.cs.command_bus.CommandBus;

@Profile("web")
@RestController
@AllArgsConstructor
public class InterviewersApiController {
    private final CommandBus commandBus;
    private final InterviewerUniqueIdentifiersQueryService interviewerUniqueIdentifiersQueryService;
    private final InterviewerRepository interviewerRepository;
    private final InterviewersPageQueryService interviewersPageQueryService;

    @PostMapping("/api/v1/interviewers/add")
    public ResponseEntity<?> addInterviewer(
            @RequestBody AddInterviewerRequestBodyDto addInterviewerRequestBodyDto
    ) {
        commandBus.submit(new AddInterviewerCommand(addInterviewerRequestBodyDto.userId()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interviewers/activate")
    public ResponseEntity<?> activateInterviewer(
            @RequestBody ActivateInterviewerRequestBodyDto activateInterviewerRequestBodyDto
    ) {
        InterviewerUniqueIdentifiersDto interviewerUniqueIds;
        try {
            interviewerUniqueIds =
                    interviewerUniqueIdentifiersQueryService.findBy(activateInterviewerRequestBodyDto.userId());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        commandBus.submit(new ActivateInterviewerCommand(interviewerUniqueIds.interviewerId()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interviewers/demote")
    public ResponseEntity<?> demoteInterviewer(
            @RequestBody DemoteInterviewerRequestBodyDto demoteInterviewerRequestBodyDto
    ) {
        InterviewerUniqueIdentifiersDto interviewerUniqueIds;
        try {
            interviewerUniqueIds =
                    interviewerUniqueIdentifiersQueryService.findBy(demoteInterviewerRequestBodyDto.userId());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        commandBus.submit(new DemoteInterviewerCommand(interviewerUniqueIds.interviewerId()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/interviewers/by-id")
    public ResponseEntity<?> getInterviewerById(
            @RequestParam(name = "interviewer_id") String interviewerId
    ) {
        Interviewer interviewer = interviewerRepository.findById(InterviewerId.hydrate(interviewerId));
        return ResponseEntity.ok().body(new GetInterviewerResponseBodyDto(InterviewerResponseDto.from(interviewer)));
    }

    @GetMapping("/api/v1/interviewers")
    public ResponseEntity<?> getAllInterviewers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletResponse response
    ) {
        InterviewersPage interviewersPage = interviewersPageQueryService.findFor(page, size);
        response.setHeader("X-Total-Count", String.valueOf(interviewersPage.totalElements()));
        return ResponseEntity.ok(
                new GetAllInterviewersResponseBodyDto(interviewersPage.content()
                                                                      .stream()
                                                                      .map(InterviewerResponseDto::from)
                                                                      .toList()));
    }
}
