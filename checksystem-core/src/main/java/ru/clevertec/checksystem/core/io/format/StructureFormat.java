package ru.clevertec.checksystem.core.io.format;

import ru.clevertec.checksystem.core.constant.Formats;

import java.util.Locale;

public enum StructureFormat {

    JSON(Formats.JSON),
    XML(Formats.XML);

    private final String serializeFormat;

    StructureFormat(String serializeFormat) {
        this.serializeFormat = serializeFormat;
    }

    public static StructureFormat from(String serializeFormat) {
        return switch (serializeFormat.trim().toLowerCase(Locale.ROOT)) {
            case Formats.JSON -> StructureFormat.JSON;
            case Formats.XML -> StructureFormat.XML;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public String toString() {
        return serializeFormat;
    }
}
