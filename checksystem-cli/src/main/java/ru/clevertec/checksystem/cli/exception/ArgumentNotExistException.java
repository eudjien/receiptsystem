package ru.clevertec.checksystem.cli.exception;

public class ArgumentNotExistException extends Exception {

    private final static String MESSAGE_FORMAT = "Argument '%s' does not exist";

    public ArgumentNotExistException(String argumentName) {
        super(String.format(MESSAGE_FORMAT, argumentName));
    }
}
