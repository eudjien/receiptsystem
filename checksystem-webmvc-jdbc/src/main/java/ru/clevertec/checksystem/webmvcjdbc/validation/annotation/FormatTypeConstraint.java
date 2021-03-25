package ru.clevertec.checksystem.webmvcjdbc.validation.annotation;

public @interface FormatTypeConstraint {
    String message() default "The combination of 'format' and 'type' is wrong";
}
