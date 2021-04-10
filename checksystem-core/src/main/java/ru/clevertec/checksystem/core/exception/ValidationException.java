package ru.clevertec.checksystem.core.exception;

import java.util.Set;

public class ValidationException extends RuntimeException {

    private final Set<Error> errors;

    public ValidationException(Set<Error> errors) {
        this.errors = errors;
    }

    public Set<Error> getErrors() {
        return errors;
    }

    public static class Error {

        private final String property;
        private final String message;

        public Error(String property, String message) {
            this.property = property;
            this.message = message;
        }

        public String getProperty() {
            return property;
        }

        public String getMessage() {
            return message;
        }
    }
}
