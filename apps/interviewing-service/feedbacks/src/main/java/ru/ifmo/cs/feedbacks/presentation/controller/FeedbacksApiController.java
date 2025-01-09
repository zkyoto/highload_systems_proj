package ru.ifmo.cs.feedbacks.presentation.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.feedbacks.application.command.CreateFeedbackForInterviewCommand;
import ru.ifmo.cs.feedbacks.application.command.RewriteFeedbackCommand;
import ru.ifmo.cs.feedbacks.application.command.SaveCommentFeedbackCommand;
import ru.ifmo.cs.feedbacks.application.command.SaveGradeFeedbackCommand;
import ru.ifmo.cs.feedbacks.application.command.SubmitFeedbackCommand;
import ru.ifmo.cs.feedbacks.application.query.FeedbackPageQueryService;
import ru.ifmo.cs.feedbacks.application.query.FeedbacksPendingResultQueryService;
import ru.ifmo.cs.feedbacks.application.query.dto.FeedbackPage;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.Grade;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.CreateFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.RewriteFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.SaveCommentFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.SaveGradeFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.SubmitFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.FeedbackPendingResultDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.FeedbackResponseDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.FeedbacksPendingResultResponseBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.GetAllFeedbacksResponseBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.GetFeedbackResponseBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class FeedbacksApiController {
    private final CommandBus commandBus;
    private final FeedbacksPendingResultQueryService feedbacksPendingResultQueryService;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackPageQueryService feedbackPageQueryService;

    @PostMapping("/api/v1/feedbacks/create")
    public ResponseEntity<?> createFeedback(
            @RequestBody CreateFeedbackRequestBodyDto createFeedbackRequestBodyDto
    ) {
        commandBus.submit(
                new CreateFeedbackForInterviewCommand(
                        createFeedbackRequestBodyDto.interviewId()
                )
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/grade/save")
    public ResponseEntity<?> saveGrade(
            @RequestBody SaveGradeFeedbackRequestBodyDto saveGradeFeedbackRequestBodyDto
    ) {
        commandBus.submit(new SaveGradeFeedbackCommand(FeedbackId.hydrate(saveGradeFeedbackRequestBodyDto.feedbackId()),
                Grade.of(saveGradeFeedbackRequestBodyDto.grade())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/comment/save")
    public ResponseEntity<?> saveComment(
            @RequestBody SaveCommentFeedbackRequestBodyDto saveCommentFeedbackRequestBodyDto
    ) {
        commandBus.submit(
                new SaveCommentFeedbackCommand(FeedbackId.hydrate(saveCommentFeedbackRequestBodyDto.feedbackId()),
                        Comment.of(saveCommentFeedbackRequestBodyDto.comment()))
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/rewrite")
    public ResponseEntity<?> rewriteFeedback(
            @RequestBody RewriteFeedbackRequestBodyDto rewriteFeedbackRequestBodyDto
    ) {
        commandBus.submit(new RewriteFeedbackCommand(FeedbackId.hydrate(rewriteFeedbackRequestBodyDto.feedbackId()),
                Grade.of(rewriteFeedbackRequestBodyDto.grade()),
                Comment.of(rewriteFeedbackRequestBodyDto.comment())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/submit")
    public ResponseEntity<?> submitFeedback(
            @RequestBody SubmitFeedbackRequestBodyDto submitFeedbackRequestBodyDto
    ) {
        commandBus.submit(new SubmitFeedbackCommand(FeedbackId.hydrate(submitFeedbackRequestBodyDto.feedbackId())));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/feedbacks/pending-result")
    public ResponseEntity<?> getFeedbacksPendingResult() {
        List<FeedbackPendingResultDto> feedbackPendingResultDtoList =
                feedbacksPendingResultQueryService.findAll()
                                                  .stream()
                                                  .map(FeedbackPendingResultDto::from)
                                                  .toList();

        return ResponseEntity.ok(new FeedbacksPendingResultResponseBodyDto(feedbackPendingResultDtoList));
    }

    @GetMapping("/api/v1/feedbacks/by-id")
    public ResponseEntity<?> getFeedbackById(
            @RequestParam("feedback_id") String feedbackId
    ) {
        Feedback feedback = feedbackRepository.findById(FeedbackId.hydrate(feedbackId));
        return ResponseEntity.ok(new GetFeedbackResponseBodyDto(FeedbackResponseDto.from(feedback)));
    }

    @GetMapping("/api/v1/feedbacks")
    public ResponseEntity<?> getFeedbacks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletResponse response
    ) {
        FeedbackPage feedbackPage = feedbackPageQueryService.findFor(page, size);
        response.setHeader("X-Total-Count", String.valueOf(feedbackPage.totalElements()));
        return ResponseEntity.ok(new GetAllFeedbacksResponseBodyDto(feedbackPage.content()
                                                                                .stream()
                                                                                .map(FeedbackResponseDto::from)
                                                                                .toList()));
    }

}