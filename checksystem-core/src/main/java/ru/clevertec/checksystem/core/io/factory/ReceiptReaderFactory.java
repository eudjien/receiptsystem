package ru.clevertec.checksystem.core.io.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.io.read.IReceiptReader;
import ru.clevertec.checksystem.core.io.read.JsonReceiptReader;
import ru.clevertec.checksystem.core.io.read.XmlReceiptReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
public final class ReceiptReaderFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private ReceiptReaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptReader instance(StructureFormat format) {

        ThrowUtils.Argument.nullValue("format", format);

        return switch (format) {
            case JSON -> applicationContext.getBean(JsonReceiptReader.class);
            case XML -> applicationContext.getBean(XmlReceiptReader.class);
        };
    }
}
