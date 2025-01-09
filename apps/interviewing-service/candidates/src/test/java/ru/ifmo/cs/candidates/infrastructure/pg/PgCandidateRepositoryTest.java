package ru.ifmo.cs.candidates.infrastructure.pg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.event.CandidateCreatedEvent;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.ifmo.cs.candidates.domain.value.CandidateStatus;
import ru.ifmo.cs.candidates.infrastructure.pg.mapper.CandidateRowMapper;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.misc.Name;

public class PgCandidateRepositoryTest {

    private PgCandidateRepository repository;

    @Mock
    private NamedParameterJdbcOperations jdbcOperations;

    @Mock
    private CandidateRowMapper rowMapper;

    @Mock
    private StoredDomainEventRepository storedDomainEventRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new PgCandidateRepository(jdbcOperations, storedDomainEventRepository, rowMapper);
    }

    @Test
    public void testFindById() {
        CandidateId candidateId = CandidateId.generate();
        Candidate mockCandidate = mock(Candidate.class);

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), eq(rowMapper)))
                .thenReturn(List.of(mockCandidate));

        Candidate result = repository.findById(candidateId);

        assertNotNull(result);
        assertEquals(mockCandidate, result);
    }

    @Test
    public void testFindById_NotFound() {
        CandidateId candidateId = CandidateId.generate();

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), eq(rowMapper)))
                .thenReturn(Collections.emptyList());

        assertThrows(Exception.class, () -> repository.findById(candidateId));
    }

    @Test
    public void testFindAll() {
        Candidate mockCandidate = mock(Candidate.class);

        when(jdbcOperations.query(anyString(), eq(rowMapper)))
                .thenReturn(List.of(mockCandidate));

        List<Candidate> results = repository.findAll();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(mockCandidate, results.get(0));
    }

    @Test
    public void testSave_NewCandidate() {
        Candidate mockCandidate = mock(Candidate.class);
        CandidateCreatedEvent event = mock(CandidateCreatedEvent.class);

        when(mockCandidate.releaseEvents()).thenReturn(List.of(event));
        when(mockCandidate.getId()).thenReturn(CandidateId.generate());
        when(mockCandidate.getCreatedAt()).thenReturn(Timestamp.valueOf("2023-10-01 00:00:00").toInstant());
        when(mockCandidate.getUpdatedAt()).thenReturn(Timestamp.valueOf("2023-10-02 00:00:00").toInstant());
        when(mockCandidate.getName()).thenReturn(Name.of("John Doe"));
        when(mockCandidate.getStatus()).thenReturn(CandidateStatus.WAITING_FOR_APPOINTMENT_AN_INTERVIEW);

        repository.save(mockCandidate);

        verify(jdbcOperations).update(anyString(), any(MapSqlParameterSource.class));
        verify(storedDomainEventRepository, times(1)).save(any());
    }

    @Test
    public void testSave_ExistingCandidate() {
        Candidate mockCandidate = mock(Candidate.class);

        when(mockCandidate.releaseEvents()).thenReturn(Collections.emptyList());
        when(mockCandidate.getId()).thenReturn(CandidateId.generate());
        when(mockCandidate.getCreatedAt()).thenReturn(Timestamp.valueOf("2023-10-01 00:00:00").toInstant());
        when(mockCandidate.getUpdatedAt()).thenReturn(Timestamp.valueOf("2023-10-02 00:00:00").toInstant());
        when(mockCandidate.getName()).thenReturn(Name.of("John Doe"));
        when(mockCandidate.getStatus()).thenReturn(CandidateStatus.WAITING_FOR_APPOINTMENT_AN_INTERVIEW);

        repository.save(mockCandidate);

        verify(jdbcOperations).update(anyString(), any(MapSqlParameterSource.class));
    }
}