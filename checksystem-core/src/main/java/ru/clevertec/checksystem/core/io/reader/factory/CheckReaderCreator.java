package ru.clevertec.checksystem.core.io.reader.factory;

import ru.clevertec.checksystem.core.io.reader.CheckReader;
import ru.clevertec.checksystem.core.io.reader.JsonCheckReader;
import ru.clevertec.checksystem.core.io.reader.XmlCheckReader;

public class CheckReaderCreator extends ReaderCreator {
    @Override
    public CheckReader create(String format) throws Exception {
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
