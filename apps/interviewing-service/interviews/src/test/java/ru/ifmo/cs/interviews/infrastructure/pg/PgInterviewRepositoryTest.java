package ru.ifmo.cs.interviews.infrastructure.pg;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.event.InterviewEvent;
import ru.ifmo.cs.interviews.domain.event.InterviewScheduledEvent;
import ru.ifmo.cs.interviews.domain.value.InterviewId;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.interviews.infrastructure.pg.mapper.PgInterviewEntityRowMapper;
import ru.ifmo.cs.interviews.infrastructure.pg.entity.PgInterviewEntity;
import ru.ifmo.cs.interviews.infrastructure.pg.entity.PgScheduleEntity;

public class PgInterviewRepositoryTest {

    private PgInterviewRepository repository;

    @Mock
    private NamedParameterJdbcOperations jdbcOperations;

    @Mock
    private PgScheduleEntityDao pgScheduleEntityDao;

    @Mock
    private PgInterviewEntityRowMapper pgInterviewEntityRowMapper;

    @Mock
    private StoredDomainEventRepository storedDomainEventRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new PgInterviewRepository(jdbcOperations, pgScheduleEntityDao, pgInterviewEntityRowMapper, storedDomainEventRepository);
    }

    @Test
    public void testFindById() {
        InterviewId interviewId = InterviewId.generate();
        PgInterviewEntity mockEntity = mock(PgInterviewEntity.class);
        List<PgScheduleEntity> scheduleEntities = Collections.emptyList();

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), eq(pgInterviewEntityRowMapper)))
                .thenReturn(List.of(mockEntity));
        when(pgScheduleEntityDao.findFor(mockEntity)).thenReturn(scheduleEntities);

        Interview mockInterview = mock(Interview.class);
        try (var staticMock = mockStatic(Interview.class)) {
            staticMock.when(() -> Interview.hydrate(mockEntity, scheduleEntities)).thenReturn(mockInterview);

            Interview result = repository.findById(interviewId);

            assertNotNull(result);
            assertEquals(mockInterview, result);
        }
    }

    @Test
    public void testFindAll() {
        PgInterviewEntity mockEntity = mock(PgInterviewEntity.class);
        List<PgScheduleEntity> scheduleEntities = Collections.emptyList();

        when(jdbcOperations.query(anyString(), eq(pgInterviewEntityRowMapper)))
                .thenReturn(List.of(mockEntity));
        when(pgScheduleEntityDao.findFor(mockEntity)).thenReturn(scheduleEntities);

        Interview mockInterview = mock(Interview.class);
        try (var staticMock = mockStatic(Interview.class)) {
            staticMock.when(() -> Interview.hydrate(mockEntity, scheduleEntities)).thenReturn(mockInterview);

            List<Interview> results = repository.findAll();

            assertNotNull(results);
            assertEquals(1, results.size());
            assertEquals(mockInterview, results.get(0));
        }
    }

    @Test
    public void testSave_NewInterview() {
        Interview mockInterview = mock(Interview.class);
        InterviewScheduledEvent event = mock(InterviewScheduledEvent.class);

        when(mockInterview.releaseEvents()).thenReturn(List.of(event));
        when(mockInterview.getId()).thenReturn(InterviewId.generate());
        when(mockInterview.getCreatedAt()).thenReturn(Instant.now());
        when(mockInterview.getUpdatedAt()).thenReturn(Instant.now());
        when(mockInterview.getInterviewerId()).thenReturn("InterviewerId");
        when(mockInterview.getCandidateId()).thenReturn("CandidateId");

        repository.save(mockInterview);

        verify(jdbcOperations).update(any(String.class), any(MapSqlParameterSource.class));
        verify(storedDomainEventRepository, times(1)).save(any());
    }

    @Test
    public void testSave_ExistingInterview() {
        Interview mockInterview = mock(Interview.class);
        InterviewEvent event = mock(InterviewEvent.class);

        when(mockInterview.releaseEvents()).thenReturn(List.of(event));
        when(mockInterview.getId()).thenReturn(InterviewId.generate());
        when(mockInterview.getCreatedAt()).thenReturn(Instant.now());
        when(mockInterview.getUpdatedAt()).thenReturn(Instant.now());
        when(mockInterview.getInterviewerId()).thenReturn("InterviewerId");
        when(mockInterview.getCandidateId()).thenReturn("CandidateId");
        repository.save(mockInterview);

        verify(jdbcOperations).update(any(String.class), any(MapSqlParameterSource.class));
        verify(storedDomainEventRepository, times(1)).save(any());
    }
}