package ru.ifmo.cs.interview_results.infrastructure.pg;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;
import ru.ifmo.cs.interview_results.domain.event.InterviewResultCreatedEvent;
import ru.ifmo.cs.interview_results.domain.event.InterviewResultEvent;
import ru.ifmo.cs.interview_results.domain.value.InterviewResultId;
import ru.ifmo.cs.interview_results.infrastructure.pg.mapper.InterviewResultRowMapper;

@Primary
@Repository
@AllArgsConstructor
public class PgInterviewResultRepository implements InterviewResultRepository {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final InterviewResultRowMapper rowMapper;
    private final StoredDomainEventRepository storedDomainEventRepository;

    private final static String FIND_BY_ID = """
            select * from interview_results
            where id=:id
            """;

    private final static String FIND_ALL = """
            select * from interview_results
            order by created_at
            """;

    private final static String INSERT = """
            INSERT INTO interview_results(
            id,
            created_at,
            updated_at,
            feedback_id,
            verdict
            ) VALUES (
            :id,
            :createdAt,
            :updatedAt,
            :feedbackId,
            :verdict
            )
            """;

    private final static String UPDATE = """
            update interview_results set
            created_at=:createdAt,
            updated_at=:updatedAt,
            feedback_id=:feedbackId,
            verdict=:verdict
            where id=:id
            """;


    @Override
    public InterviewResult findById(InterviewResultId interviewResultId) {
        return jdbcOperations.query(
                        FIND_BY_ID,
                        new MapSqlParameterSource().addValue("id", interviewResultId.value()),
                        rowMapper).stream()
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<InterviewResult> findAll() {
        return jdbcOperations.query(FIND_ALL, rowMapper);
    }

    @Override
    @Transactional
    public void save(InterviewResult interviewResult) {
        List<InterviewResultEvent> releasedEvents = interviewResult.releaseEvents();
        boolean isNew = releasedEvents.stream().anyMatch(event -> event instanceof InterviewResultCreatedEvent);
        if (isNew) {
            insert(interviewResult);
        } else {
            update(interviewResult);
        }

        releasedEvents.stream()
                .map(StoredDomainEvent::of)
                .forEach(storedDomainEventRepository::save);
    }

    private void insert(InterviewResult interviewResult) {
        jdbcOperations.update(
                INSERT,
                new MapSqlParameterSource().addValue("id", interviewResult.getId().value())
                        .addValue("createdAt", Timestamp.from(interviewResult.getCreatedAt()))
                        .addValue("updatedAt", Timestamp.from(interviewResult.getUpdatedAt()))
                        .addValue("feedbackId", interviewResult.getFeedbackId())
                        .addValue("verdict", interviewResult.getVerdict().value()));
    }

    private void update(InterviewResult interviewResult) {
        jdbcOperations.update(
                UPDATE,
                new MapSqlParameterSource().addValue("id", interviewResult.getId().value())
                        .addValue("createdAt", Timestamp.from(interviewResult.getCreatedAt()))
                        .addValue("updatedAt", Timestamp.from(interviewResult.getUpdatedAt()))
                        .addValue("feedbackId", interviewResult.getFeedbackId())
                        .addValue("verdict", interviewResult.getVerdict().value()));
    }
}
