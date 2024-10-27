package ru.itmo.cs.app.interviewing.interview.infrastructure.pg;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import ru.itmo.cs.app.interviewing.interview.infrastructure.pg.entity.PgInterviewEntity;
import ru.itmo.cs.app.interviewing.interview.infrastructure.pg.entity.PgScheduleEntity;
import ru.itmo.cs.app.interviewing.interview.infrastructure.pg.mapper.PgScheduleEntityRowMapper;

public class PgScheduleEntityDaoTest {

    private PgScheduleEntityDao dao;

    @Mock
    private NamedParameterJdbcOperations jdbcOperations;

    @Mock
    private PgScheduleEntityRowMapper pgScheduleEntityRowMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dao = new PgScheduleEntityDao(jdbcOperations, pgScheduleEntityRowMapper);
    }

    @Test
    public void testFindFor() {
        PgInterviewEntity mockInterviewEntity = mock(PgInterviewEntity.class);
        when(mockInterviewEntity.id()).thenReturn(UUID.randomUUID().toString());

        PgScheduleEntity mockScheduleEntity = mock(PgScheduleEntity.class);

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), eq(pgScheduleEntityRowMapper)))
                .thenReturn(List.of(mockScheduleEntity));

        List<PgScheduleEntity> result = dao.findFor(mockInterviewEntity);

        assertEquals(1, result.size());
        assertEquals(mockScheduleEntity, result.get(0));
    }

    @Test
    public void testSave() {
        PgScheduleEntity mockScheduleEntity = mock(PgScheduleEntity.class);
        when(mockScheduleEntity.id()).thenReturn(UUID.randomUUID().toString());
        when(mockScheduleEntity.createdAt()).thenReturn(Timestamp.valueOf("2023-10-01 00:00:00"));
        when(mockScheduleEntity.updatedAt()).thenReturn(Timestamp.valueOf("2023-10-01 00:01:00"));
        when(mockScheduleEntity.scheduledFor()).thenReturn(Timestamp.valueOf("2023-10-02 00:00:00"));
        when(mockScheduleEntity.status()).thenReturn("Scheduled");
        when(mockScheduleEntity.interviewId()).thenReturn(UUID.randomUUID().toString());

        dao.save(mockScheduleEntity);

        verify(jdbcOperations).update(anyString(), any(MapSqlParameterSource.class));
    }
}