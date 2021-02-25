package ru.clevertec.checksystem.core.exception;

public class TemplateNotSupportedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE_FORMAT = "Template not supported";

    public TemplateNotSupportedException() {
        super(DEFAULT_MESSAGE_FORMAT);
    }

    public TemplateNotSupportedException(String message) {
        super(message);
    }

    public TemplateNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateNotSupportedException(Throwable cause) {
        super(DEFAULT_MESSAGE_FORMAT, cause);
    }
}
