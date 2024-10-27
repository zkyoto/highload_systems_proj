package ru.itmo.cs.app.interviewing.candidate.presentation.controller;

import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.AbstractIntegrationTest;
import ru.itmo.cs.app.interviewing.candidate.application.command.AddCandidateCommand;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.candidate.presentation.controller.dto.request.AddCandidateRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CandidatesApiControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    CommandBus commandBus;

    @Test
    void testSuccessfullyAddingNewCandidate() throws Exception {
        Assertions.assertTrue(candidateRepository.findAll().isEmpty());
        AddCandidateRequestBodyDto requestBody = new AddCandidateRequestBodyDto("John Doe");

        mockMvc.perform(post("/api/v1/candidates/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        Assertions.assertEquals(1, candidateRepository.findAll().size());
    }

    @Test
    void testAttemptingToAddCandidateWithEmptyName() throws Exception {
        int totalCount = candidateRepository.findAll().size();

        AddCandidateRequestBodyDto requestBody = new AddCandidateRequestBodyDto("");

        mockMvc.perform(post("/api/v1/candidates/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(candidateRepository.findAll().size() == totalCount);
    }

    @Test
    void testRetrievingCandidateByValidId() throws Exception {
        commandBus.submit(new AddCandidateCommand(Name.of("my name")));
        Candidate createdCandidate = candidateRepository.findAll().stream().findAny().orElseThrow();

        mockMvc.perform(get("/api/v1/candidates/by-id")
                        .param("candidateId", createdCandidate.getId().value().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.candidate_id").value(createdCandidate.getId().value().toString()))
                .andExpect(jsonPath("$.status").value(createdCandidate.getStatus().value()))
                .andExpect(jsonPath("$.candidate_name.fullName").value(createdCandidate.getName().fullName()));
    }

    @Test
    void testRetrievingCandidateByInvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/candidates/by-id")
                        .param("candidateId", CandidateId.generate().value().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRetrievingAllCandidatesWithPagination() throws Exception {
        int totalCount = candidateRepository.findAll().size();
        int page = 0;
        int size = 50;
        Stream.of(Name.of("first name"),
                  Name.of("second name"),
                  Name.of("third name")).map(AddCandidateCommand::new)
                                                 .forEach(command -> commandBus.submit(command));

        mockMvc.perform(get("/api/v1/candidates")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", String.valueOf(totalCount + 3)));
    }

}
