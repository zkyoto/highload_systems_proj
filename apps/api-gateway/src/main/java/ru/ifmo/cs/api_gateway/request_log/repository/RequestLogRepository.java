package ru.ifmo.cs.api_gateway.request_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.cs.api_gateway.request_log.entity.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
