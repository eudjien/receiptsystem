package ru.clevertec.checksystem.core.factory;

import ru.clevertec.checksystem.core.io.reader.CheckReader;
import ru.clevertec.checksystem.core.io.reader.JsonCheckReader;
import ru.clevertec.checksystem.core.io.reader.XmlCheckReader;

public abstract class CheckReaderFactory {

    public static CheckReader create(String format) throws Exception {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }
        return switch (format) {
            case "json" -> new JsonCheckReader();
            case "xml" -> new XmlCheckReader();
            default -> throw new Exception("Format '" + format + "' not found");
        };
    }
}
