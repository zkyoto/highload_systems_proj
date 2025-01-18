package ru.ifmo.cs.interviews.presentation.controller;

import java.time.Instant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.cs.interviews.AbstractIntegrationTest;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;
import ru.ifmo.cs.interviews.application.query.InterviewsByInterviewerQueryService;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;
import ru.ifmo.cs.interviews.domain.Schedule;
import ru.ifmo.cs.interviews.presentation.controller.dto.request.CancelInterviewRequestBodyDto;
import ru.ifmo.cs.interviews.presentation.controller.dto.request.RescheduleInterviewRequestBodyDto;
import ru.ifmo.cs.interviews.presentation.controller.dto.request.ScheduleInterviewRequestBodyDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@Import(InterviewsApiControllerTest.MockKafkaConfiguration.class)
class InterviewsApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private InterviewsByInterviewerQueryService interviewsByInterviewerQueryService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ServiceTokenResolver serviceTokenResolver;

    @Test
    void testSuccessfullySchedulingInterview() throws Exception {
        Assertions.assertTrue(interviewsByInterviewerQueryService.findFor("interviewerId").interviews().isEmpty());
        ScheduleInterviewRequestBodyDto requestBody = new ScheduleInterviewRequestBodyDto(
                "interviewerId",
                "candidateId",
                Instant.now().plusSeconds(3600)
        );

        mockMvc.perform(post("/api/v1/interviews/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(2))).value()))
                .andExpect(status().isOk());
        Assertions.assertFalse(interviewsByInterviewerQueryService.findFor("interviewerId").interviews().isEmpty());
    }

    @Test
    void testSuccessfullyReschedulingInterview() throws Exception {
        Interview stubInterview = createInterview();
        Instant newScheduledTime = Instant.now().plusSeconds(7200);
        RescheduleInterviewRequestBodyDto requestBody =
                new RescheduleInterviewRequestBodyDto(stubInterview.getId().value().toString(),
                        newScheduledTime);

        mockMvc.perform(post("/api/v1/interviews/reschedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(2))).value()))
                .andExpect(status().isOk());

        Assertions.assertEquals(newScheduledTime.toEpochMilli(),
                interviewRepository.findById(stubInterview.getId()).getScheduledFor().toEpochMilli());
    }

    @Test
    void testAttemptingToRescheduleInterviewWithInvalidInterviewId() throws Exception {
        RescheduleInterviewRequestBodyDto requestBody = new RescheduleInterviewRequestBodyDto("invalid-interview-id",
                Instant.now().plusSeconds(7200));

        mockMvc.perform(post("/api/v1/interviews/reschedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(2))).value()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSuccessfullyCancelingInterview() throws Exception {
        Interview stubInterview = createInterview();
        CancelInterviewRequestBodyDto requestBody =
                new CancelInterviewRequestBodyDto(stubInterview.getId().value().toString());

        mockMvc.perform(post("/api/v1/interviews/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(2))).value()))
                .andExpect(status().isOk());

        Assertions.assertTrue(
                interviewRepository.findById(stubInterview.getId())
                        .getSchedules()
                        .stream()
                        .allMatch(Schedule::isCancelled));
    }


    public Interview createInterview() {
        Interview stubInterview = Interview.create(
                "interviewId",
                "candidateId",
                Instant.now().plusSeconds(3600)
        );
        interviewRepository.save(stubInterview);
        return stubInterview;
    }

    @MockBean(classes = {KafkaTemplate.class})
    @TestConfiguration
    public static class MockKafkaConfiguration {
    }
}