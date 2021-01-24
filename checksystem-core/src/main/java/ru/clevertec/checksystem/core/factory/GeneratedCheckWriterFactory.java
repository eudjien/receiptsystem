package ru.clevertec.checksystem.core.factory;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.io.writer.GeneratedCheckWriter;
import ru.clevertec.checksystem.core.io.writer.JsonGeneratedCheckWriter;

public abstract class GeneratedCheckWriterFactory {

    public static GeneratedCheckWriter create(String format) throws IllegalArgumentException {
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
