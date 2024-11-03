package ru.itmo.cs.app.interviewing.interview_result.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.configuration.TurnOffAllDomainEventConsumers;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.interview_result.application.query.InterviewResultByFeedbackQueryService;
import ru.itmo.cs.app.interviewing.interview_result.application.query.InterviewResultPageQueryService;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.Verdict;
import ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.request.CreateInterviewResultRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.response.GetAllInterviewResultsResponseBodyDto;
import ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.response.GetInterviewResultResponseBodyDto;
import ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.response.InterviewResultResponseDto;
import ru.itmo.cs.app.interviewing.utils.InterviewingServiceStubFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("web")
@ContextConfiguration(classes = TurnOffAllDomainEventConsumers.class)
class InterviewResultsApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InterviewResultRepository interviewResultRepository;
    @Autowired
    private InterviewingServiceStubFactory interviewingServiceStubFactory;
    @Autowired
    private InterviewResultByFeedbackQueryService interviewResultByFeedbackQueryService;
    @Autowired
    private InterviewResultPageQueryService interviewResultPageQueryService;
    @Autowired
    private ServiceTokenResolver serviceTokenResolver;

    @Test
    void testSuccessfullyCreatingInterviewResult() throws Exception {
        Feedback stubFeedback = interviewingServiceStubFactory.createFeedback();
        CreateInterviewResultRequestBodyDto requestBody = new CreateInterviewResultRequestBodyDto(
                stubFeedback.getId().value().toString(),
                Verdict.HIRE
        );

        mockMvc.perform(post("/api/v1/interview-results/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(4))).value()))
                .andExpect(status().isOk());

        Assertions.assertTrue(interviewResultByFeedbackQueryService.findByFeedbackId(stubFeedback.getId()).isPresent());
    }

    @Test
    void testRetrievingInterviewResultById() throws Exception {
        InterviewResult stubInterviewResult = interviewingServiceStubFactory.createInterviewResult();
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
        interviewingServiceStubFactory.createInterviewResult();
        interviewingServiceStubFactory.createInterviewResult();
        interviewingServiceStubFactory.createInterviewResult();


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
    void testHandleCreationOfInterviewResultWithInvalidData() throws Exception {
        CreateInterviewResultRequestBodyDto requestBody = new CreateInterviewResultRequestBodyDto("", Verdict.HIRE);

        mockMvc.perform(post("/api/v1/interview-results/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(4))).value()))
                .andExpect(status().isBadRequest());
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

}