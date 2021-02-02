package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.io.write.IGeneratedCheckWriter;
import ru.clevertec.checksystem.core.io.write.JsonGeneratedCheckWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

public final class GeneratedCheckWriterFactory {

    private GeneratedCheckWriterFactory() {
    }

    public static IGeneratedCheckWriter create(String format) throws IllegalArgumentException {

        ThrowUtils.Argument.nullOrBlank("format", format);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonGeneratedCheckWriter();
            default -> throw new ArgumentUnsupportedException("format");
        };
    }
}
