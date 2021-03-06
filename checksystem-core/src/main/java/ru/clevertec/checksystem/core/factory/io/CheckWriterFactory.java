package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.ICheckWriter;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.write.JsonCheckWriter;
import ru.clevertec.checksystem.core.io.write.XmlCheckWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import static ru.clevertec.checksystem.core.Constants.Format;

@Component
public final class CheckWriterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public CheckWriterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ICheckWriter instance(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        return switch (format) {
            case Format.JSON -> applicationContext.getBean(JsonCheckWriter.class);
            case Format.XML -> applicationContext.getBean(XmlCheckWriter.class);
            default -> throw new ArgumentNotSupportedException("format");
        };
    }
}
