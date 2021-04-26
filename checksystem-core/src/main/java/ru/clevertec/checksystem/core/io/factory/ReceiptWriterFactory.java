package ru.clevertec.checksystem.core.io.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.io.write.IReceiptWriter;
import ru.clevertec.checksystem.core.io.write.JsonReceiptWriter;
import ru.clevertec.checksystem.core.io.write.XmlReceiptWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
@RequiredArgsConstructor
public final class ReceiptWriterFactory {

    private final JsonReceiptWriter jsonReceiptWriter;
    private final XmlReceiptWriter xmlReceiptWriter;

    public IReceiptWriter instance(StructureFormat structureFormat) {

        ThrowUtils.Argument.nullValue("structureFormat", structureFormat);

        return switch (structureFormat) {
            case JSON -> jsonReceiptWriter;
            case XML -> xmlReceiptWriter;
        };
    }
}
