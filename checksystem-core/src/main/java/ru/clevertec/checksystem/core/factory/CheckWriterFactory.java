package ru.clevertec.checksystem.core.factory;

import ru.clevertec.checksystem.core.io.writer.CheckWriter;
import ru.clevertec.checksystem.core.io.writer.JsonCheckWriter;
import ru.clevertec.checksystem.core.io.writer.XmlCheckWriter;

public abstract class CheckWriterFactory {

    public static CheckWriter create(String format) throws Exception {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }
        return switch (format) {
            case "json" -> new JsonCheckWriter();
            case "xml" -> new XmlCheckWriter();
            default -> throw new Exception("Format '" + format + "' not supported");
        };
    }
}
