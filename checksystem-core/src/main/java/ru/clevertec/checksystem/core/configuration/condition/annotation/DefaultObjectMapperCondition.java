package ru.clevertec.checksystem.core.configuration.condition.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ru.clevertec.checksystem.core.configuration.condition.DefaultObjectMapperCondition.class)
public @interface DefaultObjectMapperCondition {
}
