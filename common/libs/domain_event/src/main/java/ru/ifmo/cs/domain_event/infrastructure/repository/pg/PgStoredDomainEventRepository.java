package ru.ifmo.cs.domain_event.infrastructure.repository.pg;

import java.sql.Timestamp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventStatus;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.ifmo.cs.domain_event.infrastructure.repository.pg.mapper.StoredDomainEventRowMapper;

@AllArgsConstructor
public class PgStoredDomainEventRepository implements StoredDomainEventRepository {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final StoredDomainEventRowMapper rowMapper;
    private final KnownDomainEventTypeResolver knownDomainEventTypeResolver;
    private final ObjectMapper objectMapper;

    private static final String FIND_NEXT_WAITED_FOR_DELIVERY = """
            select *
            from domain_events
            where status in (:storedStatus)
            order by event_sequence_number
            limit 1
            for update
            """;

    private static final String INSERT = """
             insert into domain_events (
             id,
             created_at,
             processed_at,
             type,
             status,
             payload,
             retry_counter
             )
             values (
             :id,
             :createdAt,
             :processedAt,
             :type,
             :status,
             :payload,
             :retryCounter
            )
            """;

    private static final String UPDATE = """
            update domain_events set
            status = :status,
            processed_at = :processedAt,
            retry_counter = :retryCounter
            where id = :id
            """;


    @Override
    public StoredDomainEvent nextWaitedForDelivery() {
        return jdbcOperations.query(FIND_NEXT_WAITED_FOR_DELIVERY, new MapSqlParameterSource().addValue(
                "storedStatus", StoredDomainEventStatus.STORED.value()), rowMapper).stream().findAny().orElse(null);
    }

    @Override
    @Transactional
    public void save(StoredDomainEvent event) {
        if (event.getEventSequenceNumber() != 0) {
            update(event);
        } else {
            insert(event);
        }
    }

    private void insert(StoredDomainEvent event) {
        String payload;
        try {
            payload = objectMapper.writeValueAsString(event.getEvent());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jdbcOperations.update(
                INSERT,
                new MapSqlParameterSource()
                        .addValue("id", event.getEventId())
                        .addValue("createdAt", Timestamp.from(event.getOccurredOn()))
                        .addValue("processedAt", event.getDeliveredAt() == null ?
                                null : Timestamp.from(event.getDeliveredAt()))
                        .addValue("type", knownDomainEventTypeResolver.typeByClass(event.getEvent().getClass()))
                        .addValue("status", event.getStatus().value())
                        .addValue("payload", payload)
                        .addValue("retryCounter", event.getRetryCounter())
        );
    }

    private void update(StoredDomainEvent event) {
        jdbcOperations.update(
                UPDATE,
                new MapSqlParameterSource()
                        .addValue("id", event.getEventId())
                        .addValue("status", event.getStatus().value())
                        .addValue("processedAt", Timestamp.from(event.getDeliveredAt()))
                        .addValue("retryCounter", event.getRetryCounter())
        );
    }
}
