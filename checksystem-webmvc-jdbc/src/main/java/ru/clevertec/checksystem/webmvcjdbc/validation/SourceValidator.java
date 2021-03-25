package ru.clevertec.checksystem.webmvcjdbc.validation;

import ru.clevertec.checksystem.webmvcjdbc.constant.Sources;
import ru.clevertec.checksystem.webmvcjdbc.validation.annotation.SourceConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class SourceValidator implements ConstraintValidator<SourceConstraint, String> {

    private SourceConstraint annotation;

    private final static Set<String> knownSources = new HashSet<>();

    static {
        knownSources.add("");
        knownSources.add(null);
        knownSources.add(Sources.FILE);
        knownSources.add(Sources.DATABASE);
    }

    @Override
    public void initialize(SourceConstraint annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String source, ConstraintValidatorContext context) {
        if (!knownSources.contains(source)) {
            context.buildConstraintViolationWithTemplate(annotation.message())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
