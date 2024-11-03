package ru.itmo.cs.app.interviewing.interview.presentation.controller;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.interview.application.query.InterviewsByInterviewerQueryService;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;
import ru.itmo.cs.app.interviewing.interview.domain.Schedule;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.CancelInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.RescheduleInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.ScheduleInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.utils.InterviewingServiceStubFactory;
import ru.itmo.cs.command_bus.CommandBus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("web")
class InterviewsApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private InterviewingServiceStubFactory stubFactory;
    @Autowired
    private InterviewsByInterviewerQueryService interviewsByInterviewerQueryService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ServiceTokenResolver serviceTokenResolver;

    @Test
    void testSuccessfullySchedulingInterview() throws Exception {
        Interviewer stubInterviewer = stubFactory.createInterviewer();
        Candidate stubCandidate = stubFactory.createCandidate();
        Assertions.assertTrue(interviewsByInterviewerQueryService.findBy(stubInterviewer.getId()).interviews().isEmpty());
        ScheduleInterviewRequestBodyDto requestBody = new ScheduleInterviewRequestBodyDto(
                stubInterviewer.getId().value().toString(),
                stubCandidate.getId().value().toString(),
                Instant.now().plusSeconds(3600)
        );

        mockMvc.perform(post("/api/v1/interviews/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(2))).value()))
                .andExpect(status().isOk());
        Assertions.assertFalse(interviewsByInterviewerQueryService.findBy(stubInterviewer.getId()).interviews().isEmpty());
    }

    @Test
    void testAttemptingToScheduleInterviewWithInvalidInterviewerId() throws Exception {
        ScheduleInterviewRequestBodyDto requestBody = new ScheduleInterviewRequestBodyDto("invalid-interviewer-id",
                UUID.randomUUID().toString(), Instant.now().plusSeconds(3600));

        mockMvc.perform(post("/api/v1/interviews/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(2))).value()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSuccessfullyReschedulingInterview() throws Exception {
        Interview stubInterview = stubFactory.createInterview();
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
        Interview stubInterview = stubFactory.createInterview();
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

}