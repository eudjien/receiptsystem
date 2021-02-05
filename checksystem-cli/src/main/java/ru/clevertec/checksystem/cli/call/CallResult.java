package ru.clevertec.checksystem.cli.call;

public class CallResult {

    private final boolean success;
    private final Exception exception;
    private final String message;

    private CallResult(boolean success, Exception exception, String message) {
        this.success = success;
        this.exception = exception;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public static CallResult success(String message) {
        return new CallResult(true, null, message);
    }

    public static CallResult fail(Exception exception, String message) {
        return new CallResult(false, exception, message);
    }
}
