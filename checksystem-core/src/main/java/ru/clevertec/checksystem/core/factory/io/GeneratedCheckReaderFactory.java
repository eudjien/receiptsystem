package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.io.read.IGeneratedCheckReader;
import ru.clevertec.checksystem.core.io.read.JsonIGeneratedCheckReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

public final class GeneratedCheckReaderFactory {

    private GeneratedCheckReaderFactory() {
    }

    public static IGeneratedCheckReader create(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Constants.Format.IO.JSON -> new JsonIGeneratedCheckReader();
            default -> throw new ArgumentUnsupportedException("format");
        };
    }
}
