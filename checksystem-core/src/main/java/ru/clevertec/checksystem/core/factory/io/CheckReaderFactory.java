package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.io.read.ICheckReader;
import ru.clevertec.checksystem.core.io.read.JsonCheckReader;
import ru.clevertec.checksystem.core.io.read.XmlCheckReader;

public abstract class CheckReaderFactory {

    public static ICheckReader create(String format) throws IllegalArgumentException {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }
        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonCheckReader();
            case Constants.Format.IO.XML -> new XmlCheckReader();
            default -> throw new IllegalArgumentException("Format '" + format + "' not supported");
        };
    }
}
