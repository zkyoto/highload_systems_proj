package ru.itmo.cs;

import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import ru.ifmo.cs.domain_event.infrastructure.repository.KnownDomainEventTypeResolver;
import ru.itmo.cs.app.interviewing.configuration.InterviewingServiceConfiguration;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

}
