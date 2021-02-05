package ru.clevertec.checksystem.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.data.DataSeed;

import static ru.clevertec.checksystem.cli.Constants.Packages;

@SpringBootApplication
@ComponentScan({Packages.CORE.ROOT, Packages.CLI.ROOT})
@EntityScan(Packages.CORE.ENTITY)
@EnableJpaRepositories(Packages.CORE.REPOSITORY)
public class ConsoleApplication implements CommandLineRunner {

    private final Application application;
    private final ArgumentsFinder argumentsFinder;

    @Autowired
    public ConsoleApplication(Application application, ArgumentsFinder argumentsFinder, DataSeed dataSeed) {
        dataSeed.dbSeed();
        this.application = application;
        this.argumentsFinder = argumentsFinder;
    }

    public static void main(String[] args) {
        // System.out.println(Arrays.stream(args).reduce((a, b) -> a + " " + b).orElse("noArgs"));
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            application.start(argumentsFinder);
        } catch (Throwable e) {
            System.err.println("Application error: " + e.getMessage());
        }
    }
}
