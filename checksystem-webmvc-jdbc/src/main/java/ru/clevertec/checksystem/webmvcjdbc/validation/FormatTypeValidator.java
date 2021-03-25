package ru.clevertec.checksystem.webmvcjdbc.validation;

import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.webmvcjdbc.validation.annotation.FormatTypeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FormatTypeValidator implements ConstraintValidator<FormatTypeConstraint, String> {

    private FormatTypeConstraint annotation;

    @Override
    public void initialize(FormatTypeConstraint annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String formatType, ConstraintValidatorContext context) {
        try {
            FormatType.parse(formatType);
            return true;
        } catch (Throwable e) {
            context.buildConstraintViolationWithTemplate(annotation.message())
                    .addConstraintViolation();
            return false;
        }
    }
}
