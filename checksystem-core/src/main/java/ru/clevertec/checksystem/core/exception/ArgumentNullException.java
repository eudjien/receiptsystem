package ru.clevertec.checksystem.core.exception;

public class ArgumentNullException extends IllegalArgumentException {

    private static final String MESSAGE_FORMAT = "Argument parameter '%s' cannot be null";

    public ArgumentNullException(String parameterName) {
        super(String.format(MESSAGE_FORMAT, parameterName));
    }
}
