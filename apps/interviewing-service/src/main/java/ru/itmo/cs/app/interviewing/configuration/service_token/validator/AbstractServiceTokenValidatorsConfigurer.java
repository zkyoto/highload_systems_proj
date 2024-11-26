package ru.itmo.cs.app.interviewing.configuration.service_token.validator;

public abstract class AbstractServiceTokenValidatorsConfigurer {
    protected static final String INTERVIEWERS_API_PATTERN = "/api/v*/interviewers/**";
    protected static final String INTERVIEWS_API_PATTERN = "/api/v*/interviews/**";
    protected static final String INTERVIEW_RESULTS_API_PATTERN = "/api/v*/interview-results/**";
    protected static final String FEEDBACKS_API_PATTERN = "/api/v*/feedbacks/**";
    protected static final String CANDIDATES_API_PATTERN = "/api/v*/candidates/**";
}
