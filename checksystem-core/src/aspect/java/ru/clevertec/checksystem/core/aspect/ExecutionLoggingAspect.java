package ru.clevertec.checksystem.core.aspect;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import ru.clevertec.checksystem.core.log.MethodSignatureFormats;
import ru.clevertec.checksystem.core.log.MethodSignatureFormatter;
import ru.clevertec.checksystem.core.log.annotation.AfterExecutionLog;
import ru.clevertec.checksystem.core.log.annotation.AroundExecutionLog;
import ru.clevertec.checksystem.core.log.annotation.BeforeExecutionLog;
import ru.clevertec.checksystem.core.util.AnnotationUtils;

@SuppressWarnings("EmptyMethod")
@Aspect
public class ExecutionLoggingAspect {

    private final static MethodSignatureFormatter formatter = new MethodSignatureFormatter();

    @Around("withinProject() && withAroundExecutionLogAnnotation() && anyPublicOperation()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        var methodSignature = getMethodSignature(pjp);
        var targetClass = methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var ann = AnnotationUtils.getPriorityAnnotation(AroundExecutionLog.class, method);

        var logger = LogManager.getLogger(targetClass);
        logger.log(ann.level().getLevel(), formatter.format(ann.beforeFormat(), method, pjp.getArgs()));
        var returnedData = pjp.proceed();
        logger.log(ann.level().getLevel(), formatter.format(ann.afterFormat(), method, pjp.getArgs(), returnedData));

        return returnedData;
    }

    @Before(value = "withinProject() && withBeforeExecutionLogAnnotation() && anyPublicOperation()")
    public void before(JoinPoint jp) {

        var methodSignature = getMethodSignature(jp);
        var targetClass = methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var ann = AnnotationUtils.getPriorityAnnotation(BeforeExecutionLog.class, method);

        var logger = LogManager.getLogger(targetClass);
        logger.log(ann.level().getLevel(), formatter.format(ann.format(), methodSignature.getMethod(), jp.getArgs()));
    }

    @AfterReturning(
            value = "withinProject() && withAfterReturningLogAnnotation() && anyPublicOperation()",
            returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {

        var methodSignature = getMethodSignature(jp);

        var targetClass = methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var ann = AnnotationUtils.getPriorityAnnotation(AfterExecutionLog.class, method);

        var logger = LogManager.getLogger(targetClass);
        logger.log(ann.level().getLevel(), formatter.format(ann.format(), method, jp.getArgs(), result));
    }

    @AfterThrowing(value = "withinProject() && anyPublicOperation()", throwing = "throwable")
    public void afterThrowing(JoinPoint jp, Throwable throwable) {

        var methodSignature = getMethodSignature(jp);

        var targetClass = methodSignature.getDeclaringType();
        var method = methodSignature.getMethod();

        var format = "[EXECUTE ERROR] - " + MethodSignatureFormats.ReturnType + " " +
                MethodSignatureFormats.MethodName +
                "(" + MethodSignatureFormats.ArgumentsTypes + ") - " +
                "(ARGS: " + MethodSignatureFormats.ArgumentsData + ")" + " - (ERROR: " + throwable.getMessage() + ")";

        var logger = LogManager.getLogger(targetClass);
        logger.error(formatter.format(format, method, jp.getArgs()));
    }

    @Pointcut("within(ru.clevertec.checksystem..*)")
    private void withinProject() {
    }

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.log.annotation.AfterExecutionLog)" +
            "|| @annotation(ru.clevertec.checksystem.core.log.annotation.AfterExecutionLog))")
    private void withAfterReturningLogAnnotation() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.log.annotation.BeforeExecutionLog)" +
            "|| @annotation(ru.clevertec.checksystem.core.log.annotation.BeforeExecutionLog))")
    private void withBeforeExecutionLogAnnotation() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.log.annotation.AroundExecutionLog)" +
            "|| @annotation(ru.clevertec.checksystem.core.log.annotation.AroundExecutionLog))")
    private void withAroundExecutionLogAnnotation() {
    }

    private static MethodSignature getMethodSignature(JoinPoint jp) {
        return (MethodSignature) (jp.getStaticPart() != null
                ? jp.getStaticPart().getSignature()
                : jp.getSignature());
    }
}
