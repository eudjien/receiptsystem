package ru.clevertec.checksystem.cli.exception;

public class ArgumentNotSupportValueException extends Exception {

    private final static String MESSAGE_FORMAT = "Argument '%s' does not support value '%s'";

    public ArgumentNotSupportValueException(String argumentName, String argumentValue) {
        super(String.format(MESSAGE_FORMAT, argumentName, argumentValue));
    }

    public ArgumentNotSupportValueException(String argumentName, String argumentValue, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, argumentName, argumentValue), cause);
    }
}
