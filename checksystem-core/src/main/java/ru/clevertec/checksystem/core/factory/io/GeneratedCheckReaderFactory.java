package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.io.read.IGeneratedCheckReader;
import ru.clevertec.checksystem.core.io.read.JsonIGeneratedCheckReader;

public abstract class GeneratedCheckReaderFactory {

    public static IGeneratedCheckReader create(String format) throws IllegalArgumentException {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }
        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonIGeneratedCheckReader();
            default -> throw new IllegalArgumentException("Format '" + format + "' not supported");
        };
    }
}
