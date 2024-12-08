package ru.ifmo.cs.api_gateway.request_log.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.ifmo.cs.api_gateway.request_log.entity.RequestLog;
import ru.ifmo.cs.api_gateway.request_log.repository.RequestLogRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class RequestLogServiceTest {

    private RequestLogRepository repository;
    private RequestLogService requestLogService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(RequestLogRepository.class);
        requestLogService = new RequestLogService(repository);
    }

    @Test
    void saveRequestLog_shouldSaveLog() {
        RequestLog log = new RequestLog();
        doReturn(log).when(repository).save(any(RequestLog.class));

        Mono<Void> result = requestLogService.saveRequestLog(log);

        StepVerifier.create(result)
                .verifyComplete();
        Mockito.verify(repository, Mockito.times(1)).save(log);
    }
}