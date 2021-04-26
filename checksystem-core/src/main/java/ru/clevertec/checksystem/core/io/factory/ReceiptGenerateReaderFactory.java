package ru.clevertec.checksystem.core.io.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.io.read.IReceiptGenerateReader;
import ru.clevertec.checksystem.core.io.read.JsonReceiptGenerateReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
@RequiredArgsConstructor
public final class ReceiptGenerateReaderFactory {

    private final JsonReceiptGenerateReader jsonReceiptGenerateReader;

    public IReceiptGenerateReader instance(GenerateFormat generateFormat) {

        ThrowUtils.Argument.nullValue("generateFormat", generateFormat);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (generateFormat) {
            case JSON -> jsonReceiptGenerateReader;
        };
    }
}
