package ru.clevertec.checksystem.webuiservlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.clevertec.checksystem.core.data.DataSeed;

import static ru.clevertec.checksystem.webuiservlet.Constants.Packages;

@SpringBootApplication
@ComponentScan({Packages.CORE.ROOT, Packages.WEB_UI_SERVLET.ROOT})
@EntityScan({Packages.CORE.ENTITY})
@EnableJpaRepositories({Packages.CORE.REPOSITORY})
@ServletComponentScan({Packages.WEB_UI_SERVLET.SERVLET})
public class ServletApplication extends SpringBootServletInitializer {

    public ServletApplication() {
    }

    @Autowired
    public ServletApplication(DataSeed dataSeed) {
        dataSeed.dbSeed();
    }

    public static void main(String[] args) {
        configureApplication(new SpringApplicationBuilder()).run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureApplication(builder);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(ServletApplication.class);
    }
}
