package ru.clevertec.checksystem.core.io.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.io.write.IReceiptGenerateWriter;
import ru.clevertec.checksystem.core.io.write.JsonReceiptGenerateWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
@RequiredArgsConstructor
public final class ReceiptGenerateWriterFactory {

    private final JsonReceiptGenerateWriter jsonReceiptGenerateWriter;

    public IReceiptGenerateWriter instance(GenerateFormat generateFormat) {

        ThrowUtils.Argument.nullValue("generateFormat", generateFormat);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (generateFormat) {
            case JSON -> jsonReceiptGenerateWriter;
        };
    }
}
