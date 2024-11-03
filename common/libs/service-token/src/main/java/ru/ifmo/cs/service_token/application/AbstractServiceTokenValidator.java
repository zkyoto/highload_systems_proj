package ru.ifmo.cs.service_token.application;

import java.util.List;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.ifmo.cs.service_token.domain.ServiceToken;

@AllArgsConstructor
public abstract class AbstractServiceTokenValidator implements HandlerInterceptor {
    private final int serviceId;
    private final List<Integer> allowedServiceIdsForRequest;
    private final ServiceTokenResolver serviceTokenResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String xServiceToken = request.getHeader("X-Service-Token");
        boolean allowed = serviceTokenResolver.resolveRequestDataFor(ServiceToken.of(xServiceToken))
                .map(requestData -> Objects.equals(requestData.dst().value(), serviceId)
                        && allowedServiceIdsForRequest.contains(requestData.src().value()))
                .orElse(false);
        if (allowed) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        response.getWriter().write("Not acceptable");
        return false;
    }

}
