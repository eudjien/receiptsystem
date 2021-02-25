package ru.clevertec.checksystem.cli.application;

import ru.clevertec.checksystem.core.common.IBuildable;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.util.Collection;
import java.util.Collections;

public class ApplicationResult {

    private final Collection<Error> errors = new SinglyLinkedList<>();

    private ApplicationResult() {
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public Collection<Error> getErrors() {
        return Collections.unmodifiableCollection(errors);
    }

    public static class Error {

        private final Exception exception;
        private final String message;

        private Error(Exception exception, String message) {
            this.exception = exception;
            this.message = message;
        }

        public Exception getException() {
            return exception;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class Builder implements IBuildable<ApplicationResult> {

        private final ApplicationResult applicationResult = new ApplicationResult();

        public void addError(Exception e, String message) {
            applicationResult.errors.add(new Error(e, message));
        }

        @Override
        public ApplicationResult build() {
            return applicationResult;
        }
    }
}
