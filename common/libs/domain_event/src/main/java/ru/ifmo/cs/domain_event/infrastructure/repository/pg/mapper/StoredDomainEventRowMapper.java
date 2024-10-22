package ru.ifmo.cs.domain_event.infrastructure.repository.pg.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import ru.ifmo.cs.domain_event.domain.DomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventStatus;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;

@AllArgsConstructor
public class StoredDomainEventRowMapper implements RowMapper<StoredDomainEvent> {
    private final KnownDomainEventTypeResolver knownDomainEventTypeResolver;
    private final ObjectMapper objectMapper;

    @Override
    public StoredDomainEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        Class<?> eventClass = knownDomainEventTypeResolver.classByType(rs.getString("type"));

        DomainEvent payload;
        try {
            payload = (DomainEvent) objectMapper.readValue(rs.getString("payload"), eventClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new StoredDomainEvent(
                UUID.fromString(rs.getString("id")),
                rs.getString("type"),
                (rs.getTimestamp("created_at").toInstant()),
                payload,
                Optional.ofNullable(rs.getTimestamp("processed_at")).map(Timestamp::toInstant).orElse(null),
                StoredDomainEventStatus.R.fromValue(rs.getString("status")),
                rs.getLong("event_sequence_number"),
                rs.getInt("retry_counter")
        );
    }
}
