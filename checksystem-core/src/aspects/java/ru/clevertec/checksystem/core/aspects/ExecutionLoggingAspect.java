package ru.clevertec.checksystem.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.AfterExecutionLog;
import ru.clevertec.checksystem.core.log.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.log.execution.BeforeExecutionLog;
import ru.clevertec.checksystem.core.log.methodlogger.MethodLogger;
import ru.clevertec.checksystem.core.log.methodlogger.MethodLoggerFormats;
import ru.clevertec.checksystem.core.utils.AnnotationUtils;

@Aspect
public class ExecutionLoggingAspect {

    @Around("anyPublicOperation() && inProject() && withAroundExecutionLogAnnotation()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        var methodSignature = getMethodSignature(pjp);

        var targetClass =  methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var ann = AnnotationUtils.getPriorityAnnotation(AroundExecutionLog.class, targetClass, method);
        var methodLogger = MethodLogger.instance(targetClass);

        methodLogger.log(ann.level(), ann.beforeFormat(), method, pjp.getArgs());

        var returnedData = pjp.proceed();

        methodLogger.log(ann.level(), ann.afterFormat(), method, pjp.getArgs(), returnedData);

        return returnedData;
    }

    @Before(value = "anyPublicOperation() && inProject() && withBeforeExecutionLogAnnotation()")
    public void before(JoinPoint jp) {

        var methodSignature = getMethodSignature(jp);

        var targetClass =  methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var ann = AnnotationUtils.getPriorityAnnotation(BeforeExecutionLog.class, targetClass, method);
        var methodLogger = MethodLogger.instance(targetClass);

        methodLogger.log(ann.level(), ann.format(), methodSignature.getMethod(), jp.getArgs());
    }

    @AfterReturning(
            value = "anyPublicOperation() && inProject() && withAfterReturningLogAnnotation()",
            returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {

        var methodSignature = getMethodSignature(jp);

        var targetClass =  methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var ann = AnnotationUtils.getPriorityAnnotation(AfterExecutionLog.class, targetClass, method);
        var methodLogger = MethodLogger.instance(targetClass);

        methodLogger.log(ann.level(), ann.format(), method, jp.getArgs(), result);
    }

    @AfterThrowing(value = "anyPublicOperation()", throwing = "throwable")
    public void afterThrowing(JoinPoint jp, Throwable throwable) {

        var methodSignature = getMethodSignature(jp);

        var targetClass =  methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var format = "[EXECUTE ERROR] - " + MethodLoggerFormats.ReturnType + " " +
                MethodLoggerFormats.MethodName +
                "(" + MethodLoggerFormats.ArgumentsTypes + ") - " +
                "(RETURN: " + MethodLoggerFormats.ReturnData + ")" + " - (ERROR: " + throwable.getMessage() + ")";

        var methodLogger = MethodLogger.instance(targetClass);
        methodLogger.log(LogLevel.ERROR, format, method, jp.getArgs());
    }

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {
    }

    @Pointcut("within(ru.clevertec.checksystem..*)")
    private void inProject() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.log.execution.AfterExecutionLog)" +
            "|| @annotation(ru.clevertec.checksystem.core.log.execution.AfterExecutionLog))")
    private void withAfterReturningLogAnnotation() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.log.execution.BeforeExecutionLog)" +
            "|| @annotation(ru.clevertec.checksystem.core.log.execution.BeforeExecutionLog))")
    private void withBeforeExecutionLogAnnotation() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.log.execution.AroundExecutionLog)" +
            "|| @annotation(ru.clevertec.checksystem.core.log.execution.AroundExecutionLog))")
    private void withAroundExecutionLogAnnotation() {
    }

    private static MethodSignature getMethodSignature(JoinPoint jp) {
        return (MethodSignature)(jp.getStaticPart() != null
                ? jp.getStaticPart().getSignature()
                : jp.getSignature());
    }
}
