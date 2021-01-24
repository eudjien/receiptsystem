package ru.clevertec.checksystem.core.factory;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.io.reader.GeneratedCheckReader;
import ru.clevertec.checksystem.core.io.reader.JsonGeneratedCheckReader;

public abstract class GeneratedCheckReaderFactory {

    public static GeneratedCheckReader create(String format) throws IllegalArgumentException {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }
        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonGeneratedCheckReader();
            default -> throw new IllegalArgumentException("Format '" + format + "' not supported");
        };
    }
}
