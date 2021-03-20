package ru.clevertec.checksystem.cli;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.checksystem.cli.constant.Packages;

@Profile("test")
@Configuration
@EnableAutoConfiguration
@ComponentScan({Packages.CORE.ROOT, Packages.CLI.ROOT})
@EntityScan(Packages.CORE.ENTITY)
@EnableJpaRepositories(Packages.CORE.REPOSITORY)
public class ConsoleTestConfiguration {
}
