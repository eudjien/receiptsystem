package ru.clevertec.checksystem.cli;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Profile("test")
@Configuration
@EnableAutoConfiguration
@ComponentScan({Constants.Packages.CORE.ROOT, Constants.Packages.CLI.ROOT})
@EntityScan(Constants.Packages.CORE.ENTITY)
@EnableJpaRepositories(Constants.Packages.CORE.REPOSITORY)
public class ConsoleTestConfiguration {
}
