package ru.clevertec.checksystem.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DefaultObjectMapper extends ObjectMapper {
    public DefaultObjectMapper() {
        registerModule(new Hibernate5Module());
    }
}
