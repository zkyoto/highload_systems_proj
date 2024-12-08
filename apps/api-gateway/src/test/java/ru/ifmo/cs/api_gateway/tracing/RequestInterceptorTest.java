package ru.ifmo.cs.api_gateway.tracing;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.ifmo.cs.api_gateway.request_log.entity.RequestLog;
import ru.ifmo.cs.api_gateway.request_log.service.RequestLogService;
import ru.ifmo.cs.api_gateway.response_log.entity.ResponseLog;
import ru.ifmo.cs.api_gateway.response_log.service.ResponseLogService;

import static org.junit.jupiter.api.Assertions.*;

class RequestInterceptorTest {

    private RequestLogService requestLogService;
    private ResponseLogService responseLogService;
    private RequestInterceptor requestInterceptor;
    ServerHttpRequest request;
    ServerHttpResponse response;

    @BeforeEach
    void setUp() {
        requestLogService = Mockito.mock(RequestLogService.class);
        responseLogService = Mockito.mock(ResponseLogService.class);
        requestInterceptor = new RequestInterceptor(requestLogService, responseLogService);

        request = Mockito.mock(ServerHttpRequest.class);
        Mockito.when(request.getMethod()).thenReturn(HttpMethod.GET);
        Mockito.when(request.getURI()).thenReturn(URI.create("http://localhost/test"));
        Mockito.when(request.getHeaders()).thenReturn(new org.springframework.http.HttpHeaders());
        Mockito.doReturn(Mono.empty()).when(requestLogService).saveRequestLog(Mockito.any(RequestLog.class));

        response = Mockito.mock(ServerHttpResponse.class);
        Mockito.when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(response.getHeaders()).thenReturn(new org.springframework.http.HttpHeaders());
        Mockito.doReturn(Mono.empty()).when(responseLogService).saveResponseLog(Mockito.any(ResponseLog.class));
    }

    @Test
    void logRequest_shouldLogRequest() {
        UUID traceId = UUID.randomUUID();
        Mono<Void> result = requestInterceptor.logRequest(request, traceId);

        StepVerifier.create(result)
                .verifyComplete();
        Mockito.verify(requestLogService, Mockito.times(1)).saveRequestLog(Mockito.any(RequestLog.class));
    }

    @Test
    void logResponse_shouldLogResponse() {
        UUID traceId = UUID.randomUUID();
        Mono<Void> result = requestInterceptor.logResponse(response, traceId);

        StepVerifier.create(result)
                .verifyComplete();
        Mockito.verify(responseLogService, Mockito.times(1)).saveResponseLog(Mockito.any(ResponseLog.class));
    }

    @Test
    void filter_shouldProcessRequestAndResponse() {
        ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class);
        GatewayFilterChain chain = Mockito.mock(GatewayFilterChain.class);

        Mockito.when(exchange.getRequest()).thenReturn(request);
        Mockito.when(exchange.getResponse()).thenReturn(response);
        Mockito.when(chain.filter(exchange)).thenReturn(Mono.empty());

        Mockito.doReturn(Mono.empty()).when(requestLogService).saveRequestLog(Mockito.any(RequestLog.class));
        Mockito.doReturn(Mono.empty()).when(responseLogService).saveResponseLog(Mockito.any(ResponseLog.class));

        Mono<Void> result = requestInterceptor.filter(exchange, chain);

        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(requestLogService, Mockito.times(1)).saveRequestLog(Mockito.any(RequestLog.class));
        Mockito.verify(responseLogService, Mockito.times(1)).saveResponseLog(Mockito.any(ResponseLog.class));
    }
}