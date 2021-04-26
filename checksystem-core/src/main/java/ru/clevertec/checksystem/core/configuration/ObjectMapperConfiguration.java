package ru.clevertec.checksystem.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfiguration {

    @Primary
    @Bean
    ObjectMapper hibernateAwareObjectMapper() {
        return new ObjectMapper().registerModule(new Hibernate5Module());
    }

    @Bean
    XmlMapper xmlObjectMapper() {
        return new XmlMapper();
    }
}
