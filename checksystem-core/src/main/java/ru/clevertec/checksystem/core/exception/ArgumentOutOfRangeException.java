package ru.clevertec.checksystem.core.exception;

public class ArgumentOutOfRangeException extends IllegalArgumentException {

    private static final String MESSAGE_FORMAT = "Argument parameter '%s' has a value '%b' that is out of range %b - %b";

    public ArgumentOutOfRangeException(String parameterName, Number value, Number minInclusive, Number maxInclusive) {
        super(String.format(MESSAGE_FORMAT, parameterName, value, minInclusive, maxInclusive));
    }

    public ArgumentOutOfRangeException(String message) {
        super(message);
    }
}
