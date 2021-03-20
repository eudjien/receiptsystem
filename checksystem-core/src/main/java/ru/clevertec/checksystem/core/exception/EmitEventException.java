package ru.clevertec.checksystem.core.exception;

public class EmitEventException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "An error occurred while emit the event";

    public EmitEventException() {
        super(DEFAULT_MESSAGE);
    }

    public EmitEventException(String message) {
        super(message);
    }

    public EmitEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmitEventException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
