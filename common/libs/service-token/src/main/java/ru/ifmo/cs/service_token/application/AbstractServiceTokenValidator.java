package ru.ifmo.cs.service_token.application;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceToken;

@Slf4j
@AllArgsConstructor
public abstract class AbstractServiceTokenValidator implements HandlerInterceptor {
    private final int serviceId;
    private final List<Integer> allowedServiceIdsForRequest;
    private final ServiceTokenResolver serviceTokenResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String xServiceToken = request.getHeader("X-Service-Token");
        Optional<RequestData> requestData = serviceTokenResolver.resolveRequestDataFor(ServiceToken.of(xServiceToken));
        Boolean allowed = requestData.map(rd -> Objects.equals(rd.dst().value(), serviceId)
                        && allowedServiceIdsForRequest.contains(rd.src().value()))
                .orElse(false);
        log.info("Validation for requestData {} : {} ", requestData, allowed);
        if (allowed) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Forbidden");
        return false;
    }

}
