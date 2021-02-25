package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.io.read.ICheckReader;
import ru.clevertec.checksystem.core.io.read.JsonCheckReader;
import ru.clevertec.checksystem.core.io.read.XmlCheckReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

public final class CheckReaderFactory {

    private CheckReaderFactory() {
    }

    public static ICheckReader create(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonCheckReader();
            case Constants.Format.IO.XML -> new XmlCheckReader();
            default -> throw new ArgumentUnsupportedException("format");
        };
    }
}
