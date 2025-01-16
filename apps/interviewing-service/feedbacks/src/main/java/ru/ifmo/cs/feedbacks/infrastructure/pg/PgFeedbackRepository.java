package ru.ifmo.cs.feedbacks.infrastructure.pg;

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
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.FeedbackRepository;
import ru.ifmo.cs.feedbacks.domain.event.FeedbackCreatedEvent;
import ru.ifmo.cs.feedbacks.domain.event.FeedbackEvent;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackId;
import ru.ifmo.cs.feedbacks.infrastructure.pg.mapper.FeedbackRowMapper;

@Primary
@Repository
@AllArgsConstructor
public class PgFeedbackRepository implements FeedbackRepository {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final FeedbackRowMapper rowMapper;
    private final StoredDomainEventRepository storedDomainEventRepository;

    private final static String FIND_BY_ID = """
            select * from feedbacks
            where id=:id
            """;

    private final static String FIND_ALL = """
            select * from feedbacks
            order by created_at
            """;

    private final static String INSERT = """
            INSERT INTO feedbacks(
            id,
            created_at,
            updated_at,
            interview_id,
            status,
            grade,
            comment,
            source_code_file_id
            ) VALUES (
            :id,
            :createdAt,
            :updatedAt,
            :interviewId,
            :status,
            :grade,
            :comment,
            :sourceCodeFileId
            )
            """;

    private final static String UPDATE = """
            update feedbacks set
            created_at=:createdAt,
            updated_at=:updatedAt,
            interview_id=:interviewId,
            status=:status,
            grade=:grade,
            comment=:comment,
            source_code_file_id=:sourceCodeFileId
            where id=:id
            """;

    @Override
    public Feedback findById(FeedbackId id) {
        return jdbcOperations.query(
                        FIND_BY_ID,
                        new MapSqlParameterSource().addValue("id", id.value()),
                        rowMapper).stream()
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<Feedback> findAll() {
        return jdbcOperations.query(FIND_ALL, rowMapper);
    }

    @Override
    @Transactional
    public void save(Feedback feedback) {
        List<FeedbackEvent> releasedEvents = feedback.releaseEvents();
        boolean isNew = releasedEvents.stream().anyMatch(event -> event instanceof FeedbackCreatedEvent);
        if (isNew) {
            insert(feedback);
        } else {
            update(feedback);
        }

        releasedEvents.stream()
                .map(StoredDomainEvent::of)
                .forEach(storedDomainEventRepository::save);
    }

    public void insert(Feedback feedback) {
        jdbcOperations.update(
                INSERT,
                new MapSqlParameterSource().addValue("id", feedback.getId().value())
                        .addValue("createdAt", Timestamp.from(feedback.getCreatedAt()))
                        .addValue("updatedAt", Timestamp.from(feedback.getUpdatedAt()))
                        .addValue("interviewId", feedback.getInterviewId())
                        .addValue("status", feedback.getStatus().value())
                        .addValue("grade", feedback.getGrade() == null ?
                                null : feedback.getGrade().getValue())
                        .addValue("comment", feedback.getComment() == null ?
                                null : feedback.getComment().getValue())
                        .addValue("sourceCodeFileId", feedback.getSourceCodeFileId()));
    }

    public void update(Feedback feedback) {
        jdbcOperations.update(
                UPDATE,
                new MapSqlParameterSource().addValue("id", feedback.getId().value())
                        .addValue("createdAt", Timestamp.from(feedback.getCreatedAt()))
                        .addValue("updatedAt", Timestamp.from(feedback.getUpdatedAt()))
                        .addValue("interviewId", feedback.getInterviewId())
                        .addValue("status", feedback.getStatus().value())
                        .addValue("grade", feedback.getGrade() == null ?
                                null : feedback.getGrade().getValue())
                        .addValue("comment", feedback.getComment() == null ?
                                null : feedback.getComment().getValue())
                        .addValue("sourceCodeFileId", feedback.getSourceCodeFileId()));
    }

}
