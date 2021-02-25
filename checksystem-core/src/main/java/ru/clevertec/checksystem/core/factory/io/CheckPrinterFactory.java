package ru.clevertec.checksystem.core.factory.io;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.io.print.CheckPrinter;
import ru.clevertec.checksystem.core.io.print.strategy.HtmlCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.print.strategy.PdfCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.print.strategy.TextCheckPrintStrategy;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.Collection;

public final class CheckPrinterFactory {

    private CheckPrinterFactory() {
    }

    public static CheckPrinter create(String format) {
        return create(format, null);
    }

    public static CheckPrinter create(String format, Collection<Check> checkCollection) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        return switch (format) {
            case Constants.Format.Print.TEXT -> new CheckPrinter(checkCollection, new TextCheckPrintStrategy());
            case Constants.Format.Print.PDF -> new CheckPrinter(checkCollection, new PdfCheckPrintStrategy());
            case Constants.Format.Print.HTML -> new CheckPrinter(checkCollection, new HtmlCheckPrintStrategy());
            default -> throw new ArgumentUnsupportedException("format");
        };
    }
}
