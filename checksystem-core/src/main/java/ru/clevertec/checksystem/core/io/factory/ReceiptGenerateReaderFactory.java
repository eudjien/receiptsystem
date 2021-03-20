package ru.clevertec.checksystem.core.io.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.io.read.IReceiptGenerateReader;
import ru.clevertec.checksystem.core.io.read.JsonReceiptGenerateReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
public final class ReceiptGenerateReaderFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private ReceiptGenerateReaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptGenerateReader instance(GenerateFormat generateFormat) {

        ThrowUtils.Argument.nullValue("generateFormat", generateFormat);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (generateFormat) {
            case JSON -> applicationContext.getBean(JsonReceiptGenerateReader.class);
        };
    }
}
