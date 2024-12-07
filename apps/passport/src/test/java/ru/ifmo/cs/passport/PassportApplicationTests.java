package ru.ifmo.cs.passport;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.passport.configuration.PassportTestsConfiguration;
import ru.ifmo.cs.passport.infrastructure.r2dbc.R2dbcPassportUserRepository;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        R2dbcAutoConfiguration.class,
        R2dbcDataAutoConfiguration.class,
        R2dbcRepositoriesAutoConfiguration.class,
        R2dbcTransactionManagerAutoConfiguration.class
})
@Import(PassportTestsConfiguration.class)
class PassportApplicationTests {

    @Mock
    R2dbcPassportUserRepository r2dbcPassportUserRepository;

    @Test
    void contextLoads() {
    }

}
