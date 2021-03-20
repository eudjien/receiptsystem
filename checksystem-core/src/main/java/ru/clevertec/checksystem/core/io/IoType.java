package ru.clevertec.checksystem.core.io;

import ru.clevertec.checksystem.core.constant.IoTypes;

import java.util.Locale;

public enum IoType {

    PRINT(IoTypes.PRINT),
    STRUCTURE(IoTypes.STRUCTURE),
    GENERATE(IoTypes.GENERATE);

    private final String ioType;

    IoType(String ioType) {
        this.ioType = ioType;
    }

    public static IoType parse(String ioType) {
        return switch (ioType.trim().toLowerCase(Locale.ROOT)) {
            case IoTypes.PRINT -> IoType.PRINT;
            case IoTypes.STRUCTURE -> IoType.STRUCTURE;
            case IoTypes.GENERATE -> IoType.GENERATE;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public String toString() {
        return ioType;
    }
}
