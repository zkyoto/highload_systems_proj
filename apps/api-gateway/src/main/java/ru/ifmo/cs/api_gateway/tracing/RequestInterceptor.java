package ru.ifmo.cs.api_gateway.tracing;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.ifmo.cs.api_gateway.request_log.entity.RequestLog;
import ru.ifmo.cs.api_gateway.request_log.service.RequestLogService;
import ru.ifmo.cs.api_gateway.response_log.entity.ResponseLog;
import ru.ifmo.cs.api_gateway.response_log.service.ResponseLogService;

@Slf4j
@Component
@AllArgsConstructor
public class RequestInterceptor implements GatewayFilter {

    private final RequestLogService requestLogService;
    private final ResponseLogService responseLogService;

    public Mono<Void> logRequest(ServerHttpRequest request, UUID traceId) {
        RequestLog requestLog = new RequestLog();
        requestLog.setMethod(request.getMethod().name());
        requestLog.setUrl(request.getURI().toString());
        requestLog.setHeaders(request.getHeaders().toString());
        requestLog.setTraceId(traceId);

        log.info("Logging request: {}", request);

        return requestLogService.saveRequestLog(requestLog);
    }

    public Mono<Void> logResponse(ServerHttpResponse response, UUID traceId) {
            ResponseLog responseLog = new ResponseLog();
            responseLog.setStatusCode(response.getStatusCode().value());
            responseLog.setHeaders(response.getHeaders().toString());
            responseLog.setTraceId(traceId);

            log.info("Logging response: {}", response);

            return responseLogService.saveResponseLog(responseLog);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            UUID traceId = UUID.randomUUID();
            this.logRequest(exchange.getRequest(), traceId).then(this.logResponse(exchange.getResponse(), traceId).then());
        }));
    }
}
