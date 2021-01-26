package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.io.print.CheckPrinter;
import ru.clevertec.checksystem.core.io.print.strategy.HtmlCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.print.strategy.PdfCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.print.strategy.TextCheckPrintStrategy;

import java.util.Collection;

public abstract class CheckPrinterFactory {

    public static CheckPrinter create(String format) throws IllegalArgumentException {
        return create(format, null);
    }

    public static CheckPrinter create(String format, Collection<Check> checkCollection)
            throws IllegalArgumentException {

        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format cannot be null or empty.");
        }

        return switch (format) {
            case Constants.Format.Print.TEXT -> new CheckPrinter(checkCollection, new TextCheckPrintStrategy());
            case Constants.Format.Print.PDF -> new CheckPrinter(checkCollection, new PdfCheckPrintStrategy());
            case Constants.Format.Print.HTML -> new CheckPrinter(checkCollection, new HtmlCheckPrintStrategy());
            default -> throw new IllegalArgumentException("Format '" + format + "' not supported");
        };
    }
}
