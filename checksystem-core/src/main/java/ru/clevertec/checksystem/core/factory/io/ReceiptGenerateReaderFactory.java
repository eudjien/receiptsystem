package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.read.IReceiptGenerateReader;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.read.JsonReceiptGenerateReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import static ru.clevertec.checksystem.core.Constants.Formats;

@Component
public final class ReceiptGenerateReaderFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private ReceiptGenerateReaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptGenerateReader instance(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Formats.JSON -> applicationContext.getBean(JsonReceiptGenerateReader.class);
            default -> throw new ArgumentNotSupportedException("format");
        };
    }
}
