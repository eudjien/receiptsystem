package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.io.write.IGeneratedCheckWriter;
import ru.clevertec.checksystem.core.io.write.JsonGeneratedCheckWriter;

public abstract class GeneratedCheckWriterFactory {

    public static IGeneratedCheckWriter create(String format) throws IllegalArgumentException {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }
        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonGeneratedCheckWriter();
            default -> throw new IllegalArgumentException("Format '" + format + "' not supported");
        };
    }
}
