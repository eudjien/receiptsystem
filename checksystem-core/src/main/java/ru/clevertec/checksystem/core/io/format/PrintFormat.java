package ru.clevertec.checksystem.core.io.format;

import ru.clevertec.checksystem.core.constant.Formats;

import java.util.Locale;

public enum PrintFormat {

    PDF(Formats.PDF),
    HTML(Formats.HTML),
    TEXT(Formats.TEXT);

    private final String printFormat;

    PrintFormat(String printFormat) {
        this.printFormat = printFormat;
    }

    public static PrintFormat parse(String printFormat) {
        return switch (printFormat.trim().toLowerCase(Locale.ROOT)) {
            case Formats.PDF -> PrintFormat.PDF;
            case Formats.HTML -> PrintFormat.HTML;
            case Formats.TEXT -> PrintFormat.TEXT;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public String toString() {
        return printFormat;
    }
}
