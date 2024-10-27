package ru.itmo.cs.app.interviewing.candidate.infrastructure.pg;

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
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.CandidateRepository;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateCreatedEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.candidate.infrastructure.pg.mapper.CandidateRowMapper;

@Primary
@Repository
@AllArgsConstructor
public class PgCandidateRepository implements CandidateRepository {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final StoredDomainEventRepository storedDomainEventRepository;
    private final CandidateRowMapper rowMapper;

    private final static String FIND_BY_ID = """
            select * from candidates
            where id=:id
            """;

    private final static String FIND_ALL = """
            select * from candidates
            order by created_at
            """;

    private final static String INSERT = """
            INSERT INTO candidates(
            id,
            full_name,
            created_at,
            updated_at,
            status
            ) VALUES (
            :id,
            :fullName,
            :createdAt,
            :updatedAt,
            :status
            )
            """;

    private final static String UPDATE = """
            update candidates set
            created_at=:createdAt,
            updated_at=:updatedAt,
            full_name=:fullName,
            status=:status
            where id=:id
            """;

    @Override
    public Candidate findById(CandidateId id) {
        return jdbcOperations.query(
                        FIND_BY_ID,
                        new MapSqlParameterSource().addValue("id", id.value()),
                        rowMapper).stream()
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<Candidate> findAll() {
        return jdbcOperations.query(FIND_ALL, rowMapper);
    }

    @Override
    @Transactional
    public void save(Candidate candidate) {
        List<CandidateEvent> releasedEvents = candidate.releaseEvents();
        boolean isNew = releasedEvents.stream().anyMatch(event -> event instanceof CandidateCreatedEvent);
        if (isNew) {
            insert(candidate);
        } else {
            update(candidate);
        }

        releasedEvents.stream()
                .map(StoredDomainEvent::of)
                .forEach(storedDomainEventRepository::save);
    }

    private void insert(Candidate candidate) {
        jdbcOperations.update(
                INSERT,
                new MapSqlParameterSource().addValue("id", candidate.getId().value())
                        .addValue("createdAt", Timestamp.from(candidate.getCreatedAt()))
                        .addValue("updatedAt", Timestamp.from(candidate.getUpdatedAt()))
                        .addValue("fullName", candidate.getName().fullName())
                        .addValue("status", candidate.getStatus().value()));
    }

    private void update(Candidate candidate) {
        jdbcOperations.update(
                UPDATE,
                new MapSqlParameterSource().addValue("id", candidate.getId().value())
                        .addValue("createdAt", Timestamp.from(candidate.getCreatedAt()))
                        .addValue("updatedAt", Timestamp.from(candidate.getUpdatedAt()))
                        .addValue("fullName", candidate.getName().fullName())
                        .addValue("status", candidate.getStatus().value()));
    }
}
