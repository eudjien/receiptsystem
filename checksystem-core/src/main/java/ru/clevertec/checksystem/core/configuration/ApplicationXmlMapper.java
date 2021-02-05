package ru.clevertec.checksystem.core.configuration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.stereotype.Component;

@Component
public class ApplicationXmlMapper extends XmlMapper {
    public ApplicationXmlMapper() {
        registerModule(new Hibernate5Module());
    }
}
