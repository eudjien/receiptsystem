package ru.clevertec.checksystem.core.log.annotation;

import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.MethodSignatureFormats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface BeforeExecutionLog {

    LogLevel level() default LogLevel.INFO;

    String format() default "[EXECUTE START] - " + MethodSignatureFormats.ReturnType + " " +
            MethodSignatureFormats.MethodName +
            "(" + MethodSignatureFormats.ArgumentsTypes + ") - " +
            "(ARGS: " + MethodSignatureFormats.ArgumentsData + ")";
}
