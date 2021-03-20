package ru.clevertec.checksystem.core.io.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.io.write.IReceiptWriter;
import ru.clevertec.checksystem.core.io.write.JsonReceiptWriter;
import ru.clevertec.checksystem.core.io.write.XmlReceiptWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
public final class ReceiptWriterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public ReceiptWriterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IReceiptWriter instance(StructureFormat structureFormat) {

        ThrowUtils.Argument.nullValue("structureFormat", structureFormat);

        return switch (structureFormat) {
            case JSON -> applicationContext.getBean(JsonReceiptWriter.class);
            case XML -> applicationContext.getBean(XmlReceiptWriter.class);
        };
    }
}
