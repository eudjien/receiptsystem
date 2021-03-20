package ru.clevertec.checksystem.core.io;

import ru.clevertec.checksystem.core.constant.Formats;
import ru.clevertec.checksystem.core.constant.IoTypes;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.Locale;

public enum FormatType {

    HTML_PRINT(Formats.HTML, IoTypes.PRINT),
    PDF_PRINT(Formats.PDF, IoTypes.PRINT),
    TEXT_PRINT(Formats.TEXT, IoTypes.PRINT),
    JSON_STRUCTURE(Formats.JSON, IoTypes.STRUCTURE),
    XML_STRUCTURE(Formats.XML, IoTypes.STRUCTURE),
    JSON_GENERATE(Formats.JSON, IoTypes.GENERATE);

    private final String format;
    private final String type;

    FormatType(String format, String type) {
        this.format = format;
        this.type = type;
    }

    public static FormatType parse(String formatType) {
        ThrowUtils.Argument.nullValue("formatType", formatType);
        var arr = formatType.split("-");
        if (arr.length != 2)
            throw new IllegalArgumentException();
        return parse(arr[0], arr[1]);
    }

    public static FormatType parse(String format, String type) {
        return switch (type.trim().toLowerCase(Locale.ROOT)) {
            case IoTypes.PRINT -> parsePrintFormatType(format);
            case IoTypes.STRUCTURE -> parseSerializeFormatType(format);
            case IoTypes.GENERATE -> parseGenerateFormatType(format);
            default -> throw new IllegalArgumentException();
        };
    }

    private static FormatType parsePrintFormatType(String format) {
        return switch (format.trim().toLowerCase(Locale.ROOT)) {
            case Formats.HTML -> FormatType.HTML_PRINT;
            case Formats.PDF -> FormatType.PDF_PRINT;
            case Formats.TEXT -> FormatType.TEXT_PRINT;
            default -> throw new IllegalArgumentException();
        };
    }

    private static FormatType parseSerializeFormatType(String format) {
        return switch (format.trim().toLowerCase(Locale.ROOT)) {
            case Formats.JSON -> FormatType.JSON_STRUCTURE;
            case Formats.XML -> FormatType.XML_STRUCTURE;
            default -> throw new IllegalArgumentException();
        };
    }

    private static FormatType parseGenerateFormatType(String format) {
        return switch (format.trim().toLowerCase(Locale.ROOT)) {
            case Formats.JSON -> FormatType.JSON_GENERATE;
            default -> throw new IllegalArgumentException();
        };
    }

    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return format + "-" + type;
    }
}
