package ru.ifmo.cs.feedbacks.presentation.controller;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.ifmo.cs.consumer.KafkaConsumerProperties;
import ru.ifmo.cs.consumer.KafkaEventsConsumer;
import ru.ifmo.cs.feedbacks.application.command.SaveCommentFeedbackCommand;
import ru.ifmo.cs.feedbacks.application.command.SaveGradeFeedbackCommand;
import ru.ifmo.cs.feedbacks.application.query.FeedbackByInterviewQueryService;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackStatus;
import ru.ifmo.cs.feedbacks.domain.value.Grade;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.CreateFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.SaveCommentFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.SaveGradeFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.request.SubmitFeedbackRequestBodyDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.FeedbackResponseDto;
import ru.ifmo.cs.feedbacks.presentation.controller.dto.response.GetFeedbackResponseBodyDto;
import ru.ifmo.cs.integration_tests.AbstractIntegrationTest;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;
import ru.itmo.cs.command_bus.CommandBus;

@MockBean(classes = {KafkaConsumerProperties.class, KafkaEventsConsumer.class})
class FeedbacksApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackByInterviewQueryService feedbackByInterviewQueryService;
    @Autowired
    CommandBus commandBus;
    @Autowired
    private ServiceTokenResolver serviceTokenResolver;

    @Test
    void testCreatingFeedbackForInterview() throws Exception {
        CreateFeedbackRequestBodyDto requestBody =
                new CreateFeedbackRequestBodyDto("interviewId");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(5))).value()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertTrue(feedbackByInterviewQueryService.findByInterviewId("interviewId").isPresent());
    }

    @Test
    void testSavingGradeForExistingFeedback() throws Exception {
        Feedback stubFeedback = createFeedback();
        SaveGradeFeedbackRequestBodyDto requestBody =
                new SaveGradeFeedbackRequestBodyDto(stubFeedback.getId().value().toString(), 4);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/grade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(5))).value()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(4, feedbackRepository.findById(stubFeedback.getId()).getGrade().getValue());
    }

    @Test
    void testSavingCommentForExistingFeedback() throws Exception {
        Feedback stubFeedback = createFeedback();
        String comment = "Great interview!";
        SaveCommentFeedbackRequestBodyDto requestBody =
                new SaveCommentFeedbackRequestBodyDto(stubFeedback.getId().value().toString(), comment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(5))).value()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(comment, feedbackRepository.findById(stubFeedback.getId()).getComment().getValue());
    }

    @Test
    void testSubmittingFeedbackForInterview() throws Exception {
        Feedback stubFeedback = createFeedback();
        commandBus.submit(new SaveGradeFeedbackCommand(stubFeedback.getId(), Grade.of(5)));
        commandBus.submit(new SaveCommentFeedbackCommand(stubFeedback.getId(), Comment.of("comment")));
        SubmitFeedbackRequestBodyDto requestBody =
                new SubmitFeedbackRequestBodyDto(stubFeedback.getId().value().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/feedbacks/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(5))).value()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(FeedbackStatus.SUBMITTED,
                feedbackRepository.findById(stubFeedback.getId()).getStatus());
    }

    @Test
    void testRetrievingFeedbackById() throws Exception {
        Feedback stubFeedback = createFeedback();
        GetFeedbackResponseBodyDto expectedResponse =
                new GetFeedbackResponseBodyDto(FeedbackResponseDto.from(stubFeedback));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/feedbacks/by-id")
                        .param("feedback_id", stubFeedback.getId().value().toString())
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(5))).value()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    public Feedback createFeedback() {
        Feedback stubFeedback = Feedback.create(UUID.randomUUID().toString());
        feedbackRepository.save(stubFeedback);
        return stubFeedback;
    }

}
