package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.io.write.ICheckWriter;
import ru.clevertec.checksystem.core.io.write.JsonCheckWriter;
import ru.clevertec.checksystem.core.io.write.XmlCheckWriter;

public abstract class CheckWriterFactory {

    public static ICheckWriter create(String format) throws IllegalArgumentException {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }
        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonCheckWriter();
            case Constants.Format.IO.XML -> new XmlCheckWriter();
            default -> throw new IllegalArgumentException("Format '" + format + "' not supported");
        };
    }
}
