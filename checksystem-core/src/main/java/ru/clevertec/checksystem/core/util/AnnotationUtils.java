package ru.clevertec.checksystem.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnotationUtils {

    private static final String REQUIRED_ANNOTATION_DOES_NOT_EXIST_MESSAGE
            = "Required annotation is not present in 'targetMethod' and its declaring class";

    public static <E extends Annotation> E getPriorityAnnotation(Class<E> annotationClass, Method targetMethod) {

        ThrowUtils.Argument.nullValue("annotationClass", annotationClass);
        ThrowUtils.Argument.nullValue("targetMethod", targetMethod);

        var targetClass = targetMethod.getDeclaringClass();

        if (!targetClass.isAnnotationPresent(annotationClass)
                && !targetMethod.isAnnotationPresent(annotationClass))
            throw new IllegalArgumentException(REQUIRED_ANNOTATION_DOES_NOT_EXIST_MESSAGE);

        return targetMethod.isAnnotationPresent(annotationClass)
                ? targetMethod.getDeclaredAnnotation(annotationClass)
                : targetClass.getAnnotation(annotationClass);
    }
}
