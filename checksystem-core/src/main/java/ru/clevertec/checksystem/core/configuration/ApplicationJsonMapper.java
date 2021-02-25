package ru.clevertec.checksystem.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.stereotype.Component;

@Component
public class ApplicationJsonMapper extends ObjectMapper {
    public ApplicationJsonMapper() {
        registerModule(new Hibernate5Module());
    }
}
