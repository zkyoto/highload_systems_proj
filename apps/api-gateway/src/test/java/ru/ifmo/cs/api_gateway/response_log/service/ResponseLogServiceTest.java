package ru.ifmo.cs.api_gateway.response_log.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.ifmo.cs.api_gateway.response_log.entity.ResponseLog;
import ru.ifmo.cs.api_gateway.response_log.repository.ResponseLogRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class ResponseLogServiceTest {

    private ResponseLogRepository repository;
    private ResponseLogService responseLogService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ResponseLogRepository.class);
        responseLogService = new ResponseLogService(repository);
    }

    @Test
    void saveResponseLog_shouldSaveLog() {
        ResponseLog log = new ResponseLog();
        doReturn(Mockito.mock(ResponseLog.class)).when(repository).save(any(ResponseLog.class));

        Mono<Void> result = responseLogService.saveResponseLog(log);

        StepVerifier.create(result)
                .verifyComplete();
        Mockito.verify(repository, Mockito.times(1)).save(log);
    }
}