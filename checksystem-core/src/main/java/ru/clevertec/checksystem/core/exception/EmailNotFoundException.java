package ru.clevertec.checksystem.core.exception;

public class EmailNotFoundException extends RuntimeException {

    private final static String MESSAGE_FORMAT = "Email '%s' not found";

    public EmailNotFoundException(String emailAddress) {
        super(String.format(MESSAGE_FORMAT, emailAddress));
    }
}
