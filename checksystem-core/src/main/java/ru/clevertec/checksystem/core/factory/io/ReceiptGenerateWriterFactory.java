package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.write.IReceiptGenerateWriter;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.write.JsonReceiptGenerateWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import static ru.clevertec.checksystem.core.Constants.Formats;

@Component
public final class ReceiptGenerateWriterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private ReceiptGenerateWriterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptGenerateWriter instance(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Formats.JSON -> applicationContext.getBean(JsonReceiptGenerateWriter.class);
            default -> throw new ArgumentNotSupportedException("format");
        };
    }
}
