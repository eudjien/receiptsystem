package ru.clevertec.checksystem.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.clevertec.checksystem.core.configuration.condition.annotation.DefaultObjectMapperCondition;
import ru.clevertec.checksystem.core.configuration.condition.annotation.HibernateAwareObjectMapperCondition;

@Configuration
public class ObjectMapperConfiguration {

    @Primary
    @Bean
    @HibernateAwareObjectMapperCondition
    ObjectMapper hibernateAwareObjectMapper() {
        return new ObjectMapper().registerModule(new Hibernate5Module());
    }

    @Primary
    @Bean
    @DefaultObjectMapperCondition
    ObjectMapper defaultObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    XmlMapper xmlObjectMapper() {
        return new XmlMapper();
    }
}
