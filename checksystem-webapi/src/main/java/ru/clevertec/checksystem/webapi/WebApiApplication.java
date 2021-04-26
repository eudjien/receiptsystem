package ru.clevertec.checksystem.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.clevertec.checksystem.core.configuration.ApplicationConfiguration;

@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class WebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}
