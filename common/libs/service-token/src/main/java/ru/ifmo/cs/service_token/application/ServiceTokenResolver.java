package ru.ifmo.cs.service_token.application;

import java.util.Optional;

import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceToken;

public interface ServiceTokenResolver {
    ServiceToken resolveServiceTokenFor(RequestData requestData);
    Optional<RequestData> resolveRequestDataFor(ServiceToken serviceToken);
}
