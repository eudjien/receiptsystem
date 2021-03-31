package ru.clevertec.checksystem.webmvcjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.checksystem.core.constant.Packages;
import ru.clevertec.checksystem.core.data.DataSeed;

@SpringBootApplication
@ComponentScan({Packages.ROOT})
@EntityScan(Packages.CORE.ENTITY)
@EnableJpaRepositories(Packages.CORE.REPOSITORY)
public class WebMvcJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebMvcJdbcApplication.class, args);
    }
}
