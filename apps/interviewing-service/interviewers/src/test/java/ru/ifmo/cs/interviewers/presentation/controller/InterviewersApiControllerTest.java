package ru.ifmo.cs.interviewers.presentation.controller;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.cs.AbstractIntegrationTest;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.stub.PassportFeignClientStub;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;
import ru.ifmo.cs.interviewers.application.command.ActivateInterviewerCommand;
import ru.ifmo.cs.interviewers.application.command.AddInterviewerCommand;
import ru.ifmo.cs.interviewers.application.command.AddInterviewerCommandHandler;
import ru.ifmo.cs.interviewers.application.query.InterviewerUniqueIdentifiersQueryService;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.ifmo.cs.interviewers.domain.InterviewerRepository;
import ru.ifmo.cs.interviewers.domain.value.InterviewerStatus;
import ru.ifmo.cs.interviewers.presentation.controller.dto.request.ActivateInterviewerRequestBodyDto;
import ru.ifmo.cs.interviewers.presentation.controller.dto.request.AddInterviewerRequestBodyDto;
import ru.ifmo.cs.interviewers.presentation.controller.dto.request.DemoteInterviewerRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = InterviewersApiControllerTest.MockConfig.class)
class InterviewersApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InterviewerRepository interviewerRepository;
    @Autowired
    private InterviewerUniqueIdentifiersQueryService interviewerUniqueIdentifiersQueryService;
    @Autowired
    CommandBus commandBus;
    @Autowired
    ServiceTokenResolver serviceTokenResolver;

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
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(3))).value()))
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
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(3))).value()))
                .andExpect(status().isOk());
        Assertions.assertEquals(InterviewerStatus.ACTIVE,
                interviewerRepository.findById(stubInterviewerIdentifiers.interviewerId()).getInterviewerStatus());
    }

    @Test
    void testAttemptingToActivateNonExistentInterviewer() throws Exception {
        ActivateInterviewerRequestBodyDto requestBody = new ActivateInterviewerRequestBodyDto(stubUserId);

        mockMvc.perform(post("/api/v1/interviewers/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(3))).value()))
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
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(3))).value()))
                .andExpect(status().isOk());
        Assertions.assertEquals(InterviewerStatus.DEMOTED,
                interviewerRepository.findById(stubInterviewerIdentifiers.interviewerId()).getInterviewerStatus());
    }

    @Test
    void testAttemptingToDemoteNonExistentInterviewer() throws Exception {
        DemoteInterviewerRequestBodyDto requestBody = new DemoteInterviewerRequestBodyDto(stubUserId);

        mockMvc.perform(post("/api/v1/interviewers/demote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .header("X-Service-Token",
                                serviceTokenResolver.resolveServiceTokenFor(new RequestData(new ServiceId(1),
                                        new ServiceId(3))).value()))
                .andExpect(status().isNotFound());
    }

    private void generateUniqueUserId() {
        stubUserId = UserId.of(new Random().nextLong());
    }

    @TestConfiguration
    static class MockConfig{
        @Primary
        @Bean
        public AddInterviewerCommandHandler addInterviewerCommandHandler(
                InterviewerRepository repository
        ) {
            return new AddInterviewerCommandHandler(repository, new PassportFeignClientStub());
        }
    }
}