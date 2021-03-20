package ru.clevertec.checksystem.core.io.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.io.write.IReceiptGenerateWriter;
import ru.clevertec.checksystem.core.io.write.JsonReceiptGenerateWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
public final class ReceiptGenerateWriterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private ReceiptGenerateWriterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptGenerateWriter instance(GenerateFormat generateFormat) {

        ThrowUtils.Argument.nullValue("generateFormat", generateFormat);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (generateFormat) {
            case JSON -> applicationContext.getBean(JsonReceiptGenerateWriter.class);
        };
    }
}
