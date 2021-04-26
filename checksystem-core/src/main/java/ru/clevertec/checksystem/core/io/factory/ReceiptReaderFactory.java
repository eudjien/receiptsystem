package ru.clevertec.checksystem.core.io.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.io.read.IReceiptReader;
import ru.clevertec.checksystem.core.io.read.JsonReceiptReader;
import ru.clevertec.checksystem.core.io.read.XmlReceiptReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
@RequiredArgsConstructor
public final class ReceiptReaderFactory {

    private final JsonReceiptReader jsonReceiptReader;
    private final XmlReceiptReader xmlReceiptReader;

    public IReceiptReader instance(StructureFormat format) {

        ThrowUtils.Argument.nullValue("format", format);

        return switch (format) {
            case JSON -> jsonReceiptReader;
            case XML -> xmlReceiptReader;
        };
    }
}
