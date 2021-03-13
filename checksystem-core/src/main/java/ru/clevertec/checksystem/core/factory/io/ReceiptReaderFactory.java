package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.read.IReceiptReader;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.read.JsonReceiptReader;
import ru.clevertec.checksystem.core.io.read.XmlReceiptReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import static ru.clevertec.checksystem.core.Constants.Formats;

@Component
public final class ReceiptReaderFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private ReceiptReaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptReader instance(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        return switch (format) {
            case Formats.JSON -> applicationContext.getBean(JsonReceiptReader.class);
            case Formats.XML -> applicationContext.getBean(XmlReceiptReader.class);
            default -> throw new ArgumentNotSupportedException("format");
        };
    }
}
