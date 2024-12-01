package ru.ifmo.cs.service_token.infrastructure;


import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;

@Slf4j
public class NotAllowedServiceTokenValidator extends AbstractServiceTokenValidator {
    public NotAllowedServiceTokenValidator() {
        super(-1, List.of(), null);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Request denied.");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Forbidden");
        return false;
    }
}
