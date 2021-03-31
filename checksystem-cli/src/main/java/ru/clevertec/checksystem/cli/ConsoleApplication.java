package ru.clevertec.checksystem.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.checksystem.cli.application.Application;
import ru.clevertec.checksystem.cli.constant.Packages;
import ru.clevertec.checksystem.core.data.DataSeed;

@Profile("!test")
@SpringBootApplication
@ComponentScan({Packages.CORE.ROOT, Packages.CLI.ROOT})
@EntityScan(Packages.CORE.ENTITY)
@EnableJpaRepositories(Packages.CORE.REPOSITORY)
public class ConsoleApplication implements CommandLineRunner {

    private final Application application;

    @Autowired
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
