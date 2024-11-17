package ru.ifmo.cs.service_token.infrastructure;


import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;

public class NotAllowedServiceTokenValidator extends AbstractServiceTokenValidator {
    public NotAllowedServiceTokenValidator() {
        super(-1, List.of(), null);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Forbidden");
        return false;
    }
}
