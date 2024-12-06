package ru.ifmo.cs.api_gateway.response_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.cs.api_gateway.response_log.entity.ResponseLog;

public interface ResponseLogRepository extends JpaRepository<ResponseLog, Long> {
}
