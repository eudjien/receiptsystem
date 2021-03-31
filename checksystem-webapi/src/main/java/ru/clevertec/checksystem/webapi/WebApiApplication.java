package ru.clevertec.checksystem.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.checksystem.webapi.constant.Packages;

@SpringBootApplication
@ComponentScan({Packages.CORE.ROOT, Packages.WEBAPI.ROOT})
@EntityScan(Packages.CORE.ENTITY)
@EnableJpaRepositories(Packages.CORE.REPOSITORY)
public class WebApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }
}
