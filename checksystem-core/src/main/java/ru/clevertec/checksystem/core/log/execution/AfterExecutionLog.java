package ru.clevertec.checksystem.core.log.execution;

import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.methodlogger.MethodLoggerFormats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AfterExecutionLog {
    String level() default LogLevel.INFO;

    String format() default "[EXECUTE END] - " + MethodLoggerFormats.ReturnType + " " +
            MethodLoggerFormats.MethodName +
            "(" + MethodLoggerFormats.ArgumentsTypes + ") - " +
            "(RETURN: " + MethodLoggerFormats.ReturnData + ")";
}
