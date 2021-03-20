package ru.clevertec.checksystem.core.annotation.execution;

import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.MethodSignatureFormats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AfterExecutionLog {

    LogLevel level() default LogLevel.INFO;

    String format() default "[EXECUTE END] - " + MethodSignatureFormats.ReturnType + " " +
            MethodSignatureFormats.MethodName +
            "(" + MethodSignatureFormats.ArgumentsTypes + ") - " +
            "(RETURN: " + MethodSignatureFormats.ReturnData + ")";
}
