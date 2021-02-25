package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.io.read.ICheckReader;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.read.JsonCheckReader;
import ru.clevertec.checksystem.core.io.read.XmlCheckReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
public final class CheckReaderFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private CheckReaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ICheckReader instance(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        return switch (format) {
            case Constants.Format.IO.JSON -> applicationContext.getBean(JsonCheckReader.class);
            case Constants.Format.IO.XML -> applicationContext.getBean(XmlCheckReader.class);
            default -> throw new ArgumentNotSupportedException("format");
        };
    }
}
