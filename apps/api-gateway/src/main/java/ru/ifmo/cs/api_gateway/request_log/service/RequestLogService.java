package ru.ifmo.cs.api_gateway.request_log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.api_gateway.request_log.entity.RequestLog;
import ru.ifmo.cs.api_gateway.request_log.repository.RequestLogRepository;

@Service
public class RequestLogService {

    private final RequestLogRepository repository;

    @Autowired
    public RequestLogService(RequestLogRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> saveRequestLog(RequestLog log) {
        repository.save(log);
        return Mono.empty();
    }
}
