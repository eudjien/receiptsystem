package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.io.write.ICheckWriter;
import ru.clevertec.checksystem.core.io.write.JsonCheckWriter;
import ru.clevertec.checksystem.core.io.write.XmlCheckWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

public final class CheckWriterFactory {

    private CheckWriterFactory() {
    }

    public static ICheckWriter create(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonCheckWriter();
            case Constants.Format.IO.XML -> new XmlCheckWriter();
            default -> throw new ArgumentUnsupportedException("format");
        };
    }
}
