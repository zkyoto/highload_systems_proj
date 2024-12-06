package ru.ifmo.cs.api_gateway.response_log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.api_gateway.response_log.entity.ResponseLog;
import ru.ifmo.cs.api_gateway.response_log.repository.ResponseLogRepository;

@Service
public class ResponseLogService {

    private final ResponseLogRepository repository;

    @Autowired
    public ResponseLogService(ResponseLogRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> saveResponseLog(ResponseLog log) {
        repository.save(log);
        return Mono.empty();
    }
}
