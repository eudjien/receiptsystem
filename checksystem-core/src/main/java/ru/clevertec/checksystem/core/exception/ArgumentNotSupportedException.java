package ru.clevertec.checksystem.core.exception;

public class ArgumentNotSupportedException extends IllegalArgumentException {

    private static final String MESSAGE_FORMAT = "Argument parameter '%s' has unsupported value";
    private static final String MESSAGE_DETAILED_FORMAT = "Argument parameter '%s' has unsupported value '%s";

    public ArgumentNotSupportedException(String parameterName) {
        super(String.format(MESSAGE_FORMAT, parameterName));
    }

    public ArgumentNotSupportedException(String parameterName, String parameterValue) {
        super(String.format(MESSAGE_DETAILED_FORMAT, parameterName, parameterValue));
    }
}
