package ru.clevertec.checksystem.core.io.format;

import ru.clevertec.checksystem.core.constant.Formats;

import java.util.Locale;

public enum GenerateFormat {

    JSON(Formats.JSON);

    private final String generateFormat;

    GenerateFormat(String generateFormat) {
        this.generateFormat = generateFormat;
    }

    public static GenerateFormat from(String generateFormat) {
        //noinspection SwitchStatementWithTooFewBranches
        return switch (generateFormat.trim().toLowerCase(Locale.ROOT)) {
            case Formats.JSON -> GenerateFormat.JSON;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public String toString() {
        return generateFormat;
    }
}
