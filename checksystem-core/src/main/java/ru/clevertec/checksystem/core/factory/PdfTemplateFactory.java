package ru.clevertec.checksystem.core.factory;

import ru.clevertec.checksystem.core.io.printer.template.pdf.FilePrintPdfTemplate;
import ru.clevertec.checksystem.core.io.printer.template.pdf.PrintPdfTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

public class PdfTemplateFactory {

    public static PrintPdfTemplate create(String template) throws URISyntaxException, IOException {

        var normalizedTemplate = template.trim().toLowerCase(Locale.ROOT);

        if (normalizedTemplate.isBlank()) {
            return null;
        }

        var values = normalizedTemplate.split("\\|");

        var path = values[0];
        var topOffset = getTopOffset(values);

        return new FilePrintPdfTemplate(path, topOffset);
    }

    private static int getTopOffset(String[] values) {
        try {
            return Integer.parseInt(values[1]);
        } catch (Exception ignored) {
        }
        return 0;
    }
}
