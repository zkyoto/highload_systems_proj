package ru.ifmo.cs.feedbacks.presentation.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.ifmo.cs.feedbacks.application.command.*;
import ru.ifmo.cs.feedbacks.application.query.FeedbackPageQueryService;
import ru.ifmo.cs.feedbacks.application.query.FeedbacksPendingResultQueryService;
import ru.ifmo.cs.feedbacks.application.query.dto.FeedbackPage;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.domain.value.Grade;
import ru.ifmo.cs.feedbacks.domain.value.SourceCodeFileId;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.*;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.*;
import ru.itmo.cs.command_bus.CommandBus;

import java.util.List;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

@OpenAPIDefinition(
        security = @SecurityRequirement(name = "Bearer Authentication"),
        servers = {
                @Server(url = "/", description = "Gateway server")
        }
)
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name = "Feedbacks API", description = "API для управления отзывами")
public class FeedbacksApiController {

    private final CommandBus commandBus;
    private final FeedbacksPendingResultQueryService feedbacksPendingResultQueryService;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackPageQueryService feedbackPageQueryService;

    @PostMapping("/api/v1/feedbacks/create")
    @Operation(summary = "Создать отзыв", description = "Создает отзыв для определенного собеседования")
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

    @PostMapping("/api/v1/feedbacks/grade")
    @Operation(summary = "Сохранить оценку", description = "Сохраняет оценку для отзыва")
    public ResponseEntity<?> saveGrade(
            @RequestBody SaveGradeFeedbackRequestBodyDto saveGradeFeedbackRequestBodyDto
    ) {
        commandBus.submit(new SaveGradeFeedbackCommand(
                FeedbackId.hydrate(saveGradeFeedbackRequestBodyDto.feedbackId()),
                Grade.of(saveGradeFeedbackRequestBodyDto.grade())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/comment")
    @Operation(summary = "Сохранить комментарий", description = "Сохраняет комментарий для отзыва")
    public ResponseEntity<?> saveComment(
            @RequestBody SaveCommentFeedbackRequestBodyDto saveCommentFeedbackRequestBodyDto
    ) {
        commandBus.submit(
                new SaveCommentFeedbackCommand(
                        FeedbackId.hydrate(saveCommentFeedbackRequestBodyDto.feedbackId()),
                        Comment.of(saveCommentFeedbackRequestBodyDto.comment()))
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/source-coude-file-id")
    @Operation(summary = "Сохранить id файла с исходным кодом", description = "Сохраняет id загруженного в file-manager файла")
    public ResponseEntity<?> saveSourceCodeFileId(
            @RequestBody SaveSourceCodeIdFeedbackRequestBodyDto saveCommentFeedbackRequestBodyDto
    ) {
        commandBus.submit(
                new SaveSourceCodeFeedbackCommand(
                        FeedbackId.hydrate(saveCommentFeedbackRequestBodyDto.feedbackId()),
                        new SourceCodeFileId(saveCommentFeedbackRequestBodyDto.sourceCodeId()))
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/rewrite")
    @Operation(summary = "Переписать отзыв", description = "Обновляет оценку и комментарий для отзыва")
    public ResponseEntity<?> rewriteFeedback(
            @RequestBody RewriteFeedbackRequestBodyDto rewriteFeedbackRequestBodyDto
    ) {
        commandBus.submit(new RewriteFeedbackCommand(
                FeedbackId.hydrate(rewriteFeedbackRequestBodyDto.feedbackId()),


                Grade.of(rewriteFeedbackRequestBodyDto.grade()),
                Comment.of(rewriteFeedbackRequestBodyDto.comment())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedbacks/submit")
    @Operation(summary = "Отправить отзыв", description = "Отправляет отзыв на рассмотрение")
    public ResponseEntity<?> submitFeedback(
            @RequestBody SubmitFeedbackRequestBodyDto submitFeedbackRequestBodyDto
    ) {
        commandBus.submit(new SubmitFeedbackCommand(
                FeedbackId.hydrate(submitFeedbackRequestBodyDto.feedbackId())));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/feedbacks/pending-result")
    @Operation(summary = "Получить ожидающие отзывы", description = "Возвращает список отзывов, ожидающих результата")
    public ResponseEntity<?> getFeedbacksPendingResult() {
        List<FeedbackPendingResultDto> feedbackPendingResultDtoList =
                feedbacksPendingResultQueryService.findAll()
                        .stream()
                        .map(FeedbackPendingResultDto::from)
                        .toList();

        return ResponseEntity.ok(new FeedbacksPendingResultResponseBodyDto(feedbackPendingResultDtoList));
    }

    @GetMapping("/api/v1/feedbacks/by-id")
    @Operation(summary = "Получить отзыв по ID", description = "Возвращает детальную информацию об отзыве по его ID")
    public ResponseEntity<?> getFeedbackById(
            @Parameter(description = "Идентификатор отзыва") @RequestParam("feedback_id") String feedbackId
    ) {
        Feedback feedback = feedbackRepository.findById(FeedbackId.hydrate(feedbackId));
        return ResponseEntity.ok(new GetFeedbackResponseBodyDto(FeedbackResponseDto.from(feedback)));
    }

    @GetMapping("/api/v1/feedbacks")
    @Operation(summary = "Получить отзывы с пагинацией", description = "Возвращает страницу с отзывами")
    public ResponseEntity<?> getFeedbacks(
            @Parameter(description = "Номер страницы") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "50") int size,
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