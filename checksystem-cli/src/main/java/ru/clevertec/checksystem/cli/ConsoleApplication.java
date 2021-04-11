package ru.clevertec.checksystem.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import ru.clevertec.checksystem.cli.application.Application;
import ru.clevertec.checksystem.core.configuration.ApplicationConfiguration;

@Profile("!test")
@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class ConsoleApplication implements CommandLineRunner {

    private final Application application;

    public ConsoleApplication(Application application) {
        this.application = application;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            application.call();
        } catch (Throwable e) {
            System.err.println("Application error: " + e.getMessage());
        }
    }
}
