package ru.clevertec.checksystem.webmvcjdbc.validation.annotation;

public @interface SourceConstraint {
    String message() default "Incompatible 'source' parameter";
}
