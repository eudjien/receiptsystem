package ru.clevertec.checksystem.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.clevertec.checksystem.core.util.mapper.HibernateAwareObjectMapper;

@Configuration
public class ObjectMapperConfiguration {

    @Primary
    @Bean
    ObjectMapper objectMapper() {
        return new HibernateAwareObjectMapper();
    }

    @Bean
    XmlMapper xmlObjectMapper() {
        return new XmlMapper();
    }
}
