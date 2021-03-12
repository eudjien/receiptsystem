package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.IReceiptWriter;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.write.JsonReceiptWriter;
import ru.clevertec.checksystem.core.io.write.XmlReceiptWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import static ru.clevertec.checksystem.core.Constants.Format;

@Component
public final class ReceiptWriterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public ReceiptWriterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptWriter instance(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        return switch (format) {
            case Format.JSON -> applicationContext.getBean(JsonReceiptWriter.class);
            case Format.XML -> applicationContext.getBean(XmlReceiptWriter.class);
            default -> throw new ArgumentNotSupportedException("format");
        };
    }
}
