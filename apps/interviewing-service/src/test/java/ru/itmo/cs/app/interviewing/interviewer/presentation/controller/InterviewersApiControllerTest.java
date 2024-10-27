package ru.itmo.cs.app.interviewing.interviewer.presentation.controller;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.interviewer.application.command.ActivateInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.command.AddInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.query.InterviewerUniqueIdentifiersQueryService;
import ru.itmo.cs.app.interviewing.interviewer.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerStatus;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.request.ActivateInterviewerRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.request.AddInterviewerRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.request.DemoteInterviewerRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InterviewersApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InterviewerRepository interviewerRepository;
    @Autowired
    private InterviewerUniqueIdentifiersQueryService interviewerUniqueIdentifiersQueryService;
    @Autowired
    CommandBus commandBus;
    private UserId stubUserId;

    @BeforeEach
    void setUp() {
        generateUniqueUserId();
    }

    @Test
    void testAddingNewInterviewerSuccessfully() throws Exception {
        AddInterviewerRequestBodyDto requestBody = new AddInterviewerRequestBodyDto(stubUserId);

        mockMvc.perform(post("/api/v1/interviewers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk());

        Assertions.assertNotNull(interviewerUniqueIdentifiersQueryService.findBy(stubUserId));
    }

    @Test
    void testActivatingExistingInterviewerSuccessfully() throws Exception {
        commandBus.submit(new AddInterviewerCommand(stubUserId));
        InterviewerUniqueIdentifiersDto stubInterviewerIdentifiers =
                interviewerUniqueIdentifiersQueryService.findBy(stubUserId);

        ActivateInterviewerRequestBodyDto requestBody = new ActivateInterviewerRequestBodyDto(stubUserId);

        mockMvc.perform(post("/api/v1/interviewers/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        Assertions.assertEquals(InterviewerStatus.ACTIVE,
                interviewerRepository.findById(stubInterviewerIdentifiers.interviewerId()).getInterviewerStatus());
    }

    @Test
    void testAttemptingToActivateNonExistentInterviewer() throws Exception {
        ActivateInterviewerRequestBodyDto requestBody = new ActivateInterviewerRequestBodyDto(stubUserId);

        mockMvc.perform(post("/api/v1/interviewers/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDemotingExistingInterviewerSuccessfully() throws Exception {
        commandBus.submit(new AddInterviewerCommand(stubUserId));
        InterviewerUniqueIdentifiersDto stubInterviewerIdentifiers =
                interviewerUniqueIdentifiersQueryService.findBy(stubUserId);
        commandBus.submit(new ActivateInterviewerCommand(stubInterviewerIdentifiers.interviewerId()));

        DemoteInterviewerRequestBodyDto requestBody = new DemoteInterviewerRequestBodyDto(stubUserId);

        mockMvc.perform(post("/api/v1/interviewers/demote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        Assertions.assertEquals(InterviewerStatus.DEMOTED,
                interviewerRepository.findById(stubInterviewerIdentifiers.interviewerId()).getInterviewerStatus());
    }

    @Test
    void testAttemptingToDemoteNonExistentInterviewer() throws Exception {
        DemoteInterviewerRequestBodyDto requestBody = new DemoteInterviewerRequestBodyDto(stubUserId);

        mockMvc.perform(post("/api/v1/interviewers/demote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    private void generateUniqueUserId() {
        stubUserId = UserId.of(new Random().nextLong());
    }
}
