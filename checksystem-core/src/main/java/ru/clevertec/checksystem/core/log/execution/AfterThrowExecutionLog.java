package ru.clevertec.checksystem.core.log.execution;

import ru.clevertec.checksystem.core.log.methodlogger.MethodLoggerFormats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AfterThrowExecutionLog {
    boolean ignore() default false;

    String format() default MethodLoggerFormats.ReturnType + " " +
            MethodLoggerFormats.MethodName +
            "(" + MethodLoggerFormats.ArgumentsTypes + ") - " +
            "(ARGS: " + MethodLoggerFormats.ArgumentsData + ")";
}
