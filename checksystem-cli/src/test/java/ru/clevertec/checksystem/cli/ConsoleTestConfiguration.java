package ru.clevertec.checksystem.cli;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import ru.clevertec.checksystem.cli.constant.Packages;
import ru.clevertec.checksystem.core.configuration.ApplicationConfiguration;

@Profile("test")
@Configuration
@EnableAutoConfiguration
@Import(ApplicationConfiguration.class)
@ComponentScan({Packages.CLI.ROOT})
public class ConsoleTestConfiguration {
}
