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
import ru.clevertec.checksystem.core.data.DataSeed;

@Profile("!test")
@SpringBootApplication
@ComponentScan({Constants.Packages.CORE.ROOT, Constants.Packages.CLI.ROOT})
@EntityScan(Constants.Packages.CORE.ENTITY)
@EnableJpaRepositories(Constants.Packages.CORE.REPOSITORY)
public class ConsoleApplication implements CommandLineRunner {

    private final Application application;

    @Autowired
    public ConsoleApplication(Application application, DataSeed dataSeed) {
        dataSeed.dbSeed();
        this.application = application;
    }

    public static void main(String[] args) {
        //System.out.println(Arrays.stream(args).reduce((a, b) -> a + " " + b).orElse("noArgs"));
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
