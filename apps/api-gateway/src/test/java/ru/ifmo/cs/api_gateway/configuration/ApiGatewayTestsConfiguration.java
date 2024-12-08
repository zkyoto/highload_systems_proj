package ru.ifmo.cs.api_gateway.configuration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.ifmo.cs.api_gateway.request_log.repository.RequestLogRepository;
import ru.ifmo.cs.api_gateway.response_log.repository.ResponseLogRepository;
import ru.ifmo.cs.api_gateway.stub.StubRequestLogRepository;
import ru.ifmo.cs.api_gateway.stub.StubResponseLogRepository;

@TestConfiguration
public class ApiGatewayTestsConfiguration {

    @Primary
    @Bean
    public RequestLogRepository requestLogRepository() {
        return new StubRequestLogRepository();
    }

    @Primary
    @Bean
    public ResponseLogRepository responseLogRepository() {
        return new StubResponseLogRepository();
    }

}
