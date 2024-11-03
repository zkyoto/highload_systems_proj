package ru.ifmo.cs.service_token.infrastructure;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;
import ru.ifmo.cs.service_token.domain.ServiceToken;

public class StubServiceTokenResolverImpl implements ServiceTokenResolver {
    private static final int API_GATEWAY_SERVICE_ID = 1;
    private static final int INTERVIEWS_SERVICE_ID = 2;
    private static final int INTERVIEWERS_SERVICE_ID = 3;
    private static final int INTERVIEW_RESULTS_SERVICE_ID = 4;
    private static final int FEEDBACKS_SERVICE_ID = 5;
    private static final int CANDIDATES_SERVICE_ID = 6;

    private final Map<ServiceToken, RequestData> tokensForRequest;

    public StubServiceTokenResolverImpl() {
//        Token token5 = new Token("74975104-f909-411a-a653-31483ffecf87");
//        Token token6 = new Token("6a4a2240-6f07-4592-bbf1-8a9d57862c59");
//        Token token7 = new Token("b9a1b746-0dce-4a98-a4f7-c5a8d652ddd6");
//        Token token8 = new Token("337b862b-2843-41dd-bbfc-c28e27c1da48");
//        Token token9 = new Token("2fc4fe75-97f5-4a25-99d2-7a6dd3046f59");
        this.tokensForRequest = Map.of(
                new ServiceToken("206b4034-5c5f-4447-8d94-33e51e053c08"),
                new RequestData(new ServiceId(API_GATEWAY_SERVICE_ID), new ServiceId(INTERVIEWS_SERVICE_ID)),
                new ServiceToken("960e40a8-e1a3-4c3a-8d8b-32eeac4e893c"),
                new RequestData(new ServiceId(API_GATEWAY_SERVICE_ID), new ServiceId(INTERVIEWERS_SERVICE_ID)),
                new ServiceToken("09bc2b11-656c-446c-9a4d-89c28df53450"),
                new RequestData(new ServiceId(API_GATEWAY_SERVICE_ID), new ServiceId(INTERVIEW_RESULTS_SERVICE_ID)),
                new ServiceToken("17695ddc-78e5-4895-bc35-83bac92960df"),
                new RequestData(new ServiceId(API_GATEWAY_SERVICE_ID), new ServiceId(FEEDBACKS_SERVICE_ID)),
                new ServiceToken("bbfcaa46-b0ca-4f77-8a28-f98b8a3eedd4"),
                new RequestData(new ServiceId(API_GATEWAY_SERVICE_ID), new ServiceId(CANDIDATES_SERVICE_ID))
        );
    }

    @Override
    public ServiceToken resolveServiceTokenFor(RequestData requestData) {
        return tokensForRequest.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(requestData))
                .findAny()
                .map(Map.Entry::getKey)
                .orElse(new ServiceToken(UUID.randomUUID().toString()));
    }

    @Override
    public Optional<RequestData> resolveRequestDataFor(ServiceToken serviceToken) {
        return Optional.ofNullable(tokensForRequest.get(serviceToken));
    }

}
