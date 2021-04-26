package ru.clevertec.checksystem.core.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.checksystem.core.constant.Packages;

@Log4j2
@Configuration
@ComponentScan(Packages.CORE.ROOT)
@EntityScan(Packages.CORE.ENTITY)
@EnableJpaRepositories(Packages.CORE.REPOSITORY)
public class ApplicationConfiguration {

//    @ConditionalOnProperty(prefix = "application", name = "database.seed-programmatically")
//    @EventListener(value = ApplicationReadyEvent.class)
//    public void databaseSeed(ApplicationReadyEvent event) throws ParseException {
//        event.getApplicationContext().getBean(DataSeed.class).dbSeed();
//        log.info("Database successfully seeded programmatically.");
//    }
}
