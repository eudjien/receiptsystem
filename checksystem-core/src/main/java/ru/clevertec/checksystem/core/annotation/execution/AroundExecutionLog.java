package ru.clevertec.checksystem.core.annotation.execution;

import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.MethodSignatureFormats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AroundExecutionLog {

    LogLevel level() default LogLevel.INFO;

    String beforeFormat() default "[EXECUTE START] - " + MethodSignatureFormats.ReturnType + " " +
            MethodSignatureFormats.MethodName +
            "(" + MethodSignatureFormats.ArgumentsTypes + ") - " +
            "(ARGS: " + MethodSignatureFormats.ArgumentsData + ")";

    String afterFormat() default "[EXECUTE END] - " + MethodSignatureFormats.ReturnType + " " +
            MethodSignatureFormats.MethodName +
            "(" + MethodSignatureFormats.ArgumentsTypes + ") - " +
            "(RETURN: " + MethodSignatureFormats.ReturnData + ")";
}
