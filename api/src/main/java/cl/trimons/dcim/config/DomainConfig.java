package cl.trimons.dcim.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan("cl.trimons.dcim.model")
@EntityScan("cl.trimons.dcim.domain")
@EnableJpaRepositories("cl.trimons.dcim.repos")
@EnableTransactionManagement
public class DomainConfig {
}
