package ru.ifmo.cs.candidates.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.cs.candidates.domain.CandidateRepository;

import static org.junit.jupiter.api.Assertions.*;

class CandidateByInterviewResultQueryServiceImplTest {
    CandidateByInterviewResultQueryServiceImpl candidateByInterviewResultQueryService;

    @BeforeEach
    void setUp() {
        candidateByInterviewResultQueryService =
                new CandidateByInterviewResultQueryServiceImpl(Mockito.mock(CandidateRepository.class));
    }

    @Test
    void test() {
        assertNull(candidateByInterviewResultQueryService.findFor("zxc"));
    }
}