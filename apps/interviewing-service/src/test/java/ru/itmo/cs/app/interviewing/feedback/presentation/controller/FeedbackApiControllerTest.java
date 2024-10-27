package ru.itmo.cs.app.interviewing.feedback.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.configuration.TurnOffAllDomainEventConsumers;
import ru.itmo.cs.app.interviewing.feedback.application.command.SaveCommentFeedbackCommand;
import ru.itmo.cs.app.interviewing.feedback.application.command.SaveGradeFeedbackCommand;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbackByInterviewQueryService;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackStatus;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.CreateFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.SaveCommentFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.SaveGradeFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.SubmitFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response.FeedbackResponseDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response.GetFeedbackResponseBodyDto;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.utils.InterviewingServiceStubFactory;
import ru.itmo.cs.command_bus.CommandBus;

@ContextConfiguration(classes = TurnOffAllDomainEventConsumers.class)
class FeedbackApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackByInterviewQueryService feedbackByInterviewQueryService;
    @Autowired
    private InterviewingServiceStubFactory stubFactory;
    @Autowired
    CommandBus commandBus;

    @Test
    void testCreatingFeedbackForInterview() throws Exception {
        Interview stubInterview = stubFactory.createInterview();
        CreateFeedbackRequestBodyDto requestBody =
                new CreateFeedbackRequestBodyDto(stubInterview.getId().value().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertTrue(feedbackByInterviewQueryService.findByInterviewId(stubInterview.getId()).isPresent());
    }

    @Test
    void testSavingGradeForExistingFeedback() throws Exception {
        Feedback stubFeedback = stubFactory.createFeedback();
        SaveGradeFeedbackRequestBodyDto requestBody =
                new SaveGradeFeedbackRequestBodyDto(stubFeedback.getId().value().toString(), 4);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/grade/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(4, feedbackRepository.findById(stubFeedback.getId()).getGrade().getValue());
    }

    @Test
    void testSavingCommentForExistingFeedback() throws Exception {
        Feedback stubFeedback = stubFactory.createFeedback();
        String comment = "Great interview!";
        SaveCommentFeedbackRequestBodyDto requestBody =
                new SaveCommentFeedbackRequestBodyDto(stubFeedback.getId().value().toString(), comment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(comment, feedbackRepository.findById(stubFeedback.getId()).getComment().getValue());
    }

    @Test
    void testSubmittingFeedbackForInterview() throws Exception {
        Feedback stubFeedback = stubFactory.createFeedback();
        commandBus.submit(new SaveGradeFeedbackCommand(stubFeedback.getId(), Grade.of(5)));
        commandBus.submit(new SaveCommentFeedbackCommand(stubFeedback.getId(), Comment.of("comment")));
        SubmitFeedbackRequestBodyDto requestBody =
                new SubmitFeedbackRequestBodyDto(stubFeedback.getId().value().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(FeedbackStatus.SUBMITTED,
                feedbackRepository.findById(stubFeedback.getId()).getStatus());
    }

    @Test
    void testRetrievingFeedbackById() throws Exception {
        Feedback stubFeedback = stubFactory.createFeedback();
        GetFeedbackResponseBodyDto expectedResponse =
                new GetFeedbackResponseBodyDto(FeedbackResponseDto.from(stubFeedback));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/feedbacks/by-id")
                        .param("feedback_id", stubFeedback.getId().value().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

}
