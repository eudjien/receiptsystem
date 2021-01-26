package ru.clevertec.checksystem.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtils {

    public static <E extends Annotation> E getPriorityAnnotation(Class<E> annotationClass, Method targetMethod)
            throws IllegalArgumentException {

        if (annotationClass == null) {
            throw new IllegalArgumentException("Argument 'annotationClass' cannot be null");
        }

        if (targetMethod == null) {
            throw new IllegalArgumentException("Argument 'targetMethod' cannot be null");
        }

        var targetClass = targetMethod.getDeclaringClass();

        if (!targetClass.isAnnotationPresent(annotationClass)
                && !targetMethod.isAnnotationPresent(annotationClass)) {
            throw new IllegalArgumentException(
                    "Required annotation is not present in 'targetMethod' and its declaring class");
        }

        return targetMethod.isAnnotationPresent(annotationClass)
                ? targetMethod.getDeclaredAnnotation(annotationClass)
                : targetClass.getAnnotation(annotationClass);
    }
}
