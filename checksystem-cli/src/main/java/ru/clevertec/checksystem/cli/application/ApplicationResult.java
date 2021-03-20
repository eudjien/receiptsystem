package ru.clevertec.checksystem.cli.application;

import ru.clevertec.checksystem.core.common.IBuildable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ApplicationResult {

    private final Collection<Error> errors = new ArrayList<>();

    private ApplicationResult() {
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public Collection<Error> getErrors() {
        return Collections.unmodifiableCollection(errors);
    }

    public static class Error {

        private final Throwable throwable;
        private final String message;

        private Error(Throwable throwable, String message) {
            this.throwable = throwable;
            this.message = message;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class Builder implements IBuildable<ApplicationResult> {

        private final ApplicationResult applicationResult = new ApplicationResult();

        public void addError(Throwable e, String message) {
            applicationResult.errors.add(new Error(e, message));
        }

        @Override
        public ApplicationResult build() {
            return applicationResult;
        }
    }
}
