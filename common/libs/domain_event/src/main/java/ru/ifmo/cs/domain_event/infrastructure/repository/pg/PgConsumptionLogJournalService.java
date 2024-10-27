package ru.ifmo.cs.domain_event.infrastructure.repository.pg;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.ifmo.cs.domain_event.application.service.ConsumptionLogJournalService;
import ru.ifmo.cs.domain_event.domain.consumed_event.ConsumedDomainEvent;

@AllArgsConstructor
public class PgConsumptionLogJournalService implements ConsumptionLogJournalService {
    private final NamedParameterJdbcOperations jdbcOperations;

    private static final String QUERY = """
            insert into domain_event_consumption_logs
            (event_id, consumed_at, subscriber_reference_id)
            values (:eventId, :consumedAt, :subscriberReferenceId)
            on conflict do nothing
            """;

    @Override
    public boolean acquireFor(ConsumedDomainEvent consumedDomainEvent) {
        return jdbcOperations.update(
                QUERY,
                new MapSqlParameterSource().addValue("eventId", consumedDomainEvent.eventId())
                        .addValue("consumedAt", Timestamp.from(consumedDomainEvent.consumedAt()))
                        .addValue("subscriberReferenceId", consumedDomainEvent.referenceId().id())
        ) == 0;
    }
}
