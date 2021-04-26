package ru.clevertec.checksystem.cli.application;

import lombok.Value;
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

    @Value
    public static class Error {
        Throwable throwable;
        String message;
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
