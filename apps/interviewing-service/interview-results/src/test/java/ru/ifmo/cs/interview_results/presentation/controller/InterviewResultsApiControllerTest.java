package ru.ifmo.cs.interview_results.presentation.controller;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.cs.AbstractIntegrationTest;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;
import ru.ifmo.cs.interview_results.application.query.InterviewResultByFeedbackQueryService;
import ru.ifmo.cs.interview_results.application.query.InterviewResultPageQueryService;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;
import ru.ifmo.cs.interview_results.domain.value.InterviewResultId;
import ru.ifmo.cs.interview_results.domain.value.Verdict;
import ru.ifmo.cs.interview_results.presentation.controller.dto.request.CreateInterviewResultRequestBodyDto;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.GetAllInterviewResultsResponseBodyDto;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.GetInterviewResultResponseBodyDto;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.InterviewResultResponseDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(classes = TurnOffAllDomainEventConsumers.class)
@Import(InterviewResultsApiControllerTest.MockKafkaConfiguration.class)
class InterviewResultsApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InterviewResultRepository interviewResultRepository;
    @Autowired
    private InterviewResultByFeedbackQueryService interviewResultByFeedbackQueryService;
    @Autowired
    private InterviewResultPageQueryService interviewResultPageQueryService;
    @Autowired
    private ServiceTokenResolver serviceTokenResolver;

    @Test
    void testSuccessfullyCreatingInterviewResult() throws Exception {
        CreateInterviewResultRequestBodyDto requestBody = new CreateInterviewResultRequestBodyDto(
                "feedbackId",
                Verdict.HIRE
        );

        mockMvc.perform(post("/api/v1/interview-results/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(4))).value()))
                .andExpect(status().isOk());

        Assertions.assertTrue(interviewResultByFeedbackQueryService.findByFeedbackId("feedbackId").isPresent());
    }

    @Test
    void testRetrievingInterviewResultById() throws Exception {
        InterviewResult stubInterviewResult = createInterviewResult();
        GetInterviewResultResponseBodyDto expectedResponseContent =
                new GetInterviewResultResponseBodyDto(InterviewResultResponseDto.from(stubInterviewResult));

        mockMvc.perform(get("/api/v1/interview-results/by-id")
                        .param("interview_result_id", stubInterviewResult.getId().value().toString())
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(4))).value()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponseContent)));
    }

    @Test
    void testRetrievingAllInterviewResultsWithPagination() throws Exception {
        int totalCount = interviewResultRepository.findAll().size();
        createInterviewResult();
        createInterviewResult();
        createInterviewResult();


        mockMvc.perform(get("/api/v1/interview-results")
                        .param("page", "0")
                        .param("size", "5")
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(4))).value()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(
                        new GetAllInterviewResultsResponseBodyDto(
                                interviewResultPageQueryService.findFor(0, 5)
                                        .content()
                                        .stream()
                                        .map(InterviewResultResponseDto::from)
                                        .toList()
                        )
                )))
                .andExpect(header().string("X-Total-Count", String.valueOf(totalCount + 3)));
    }

    @Test
    void testRetrievingInterviewResultWithNonExistentId() throws Exception {
        mockMvc.perform(get("/api/v1/interview-results/by-id")
                        .param("interview_result_id", InterviewResultId.generate().value().toString())
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(4))).value()))
                .andExpect(status().isNotFound());
    }

    public InterviewResult createInterviewResult() {
        InterviewResult stubInterviewResult = InterviewResult.create(UUID.randomUUID().toString(), Verdict.HIRE);
        interviewResultRepository.save(stubInterviewResult);
        return stubInterviewResult;
    }

    @MockBean(classes = {KafkaTemplate.class})
    @TestConfiguration
    public static class MockKafkaConfiguration {
    }
}