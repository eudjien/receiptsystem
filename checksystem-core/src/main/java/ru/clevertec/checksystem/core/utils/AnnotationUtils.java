package ru.clevertec.checksystem.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtils {

    public static <E extends Annotation> E getPriorityAnnotation(Class<E> annotationClass,
                                                                 Class<?> targetClass,
                                                                 Method method) {

        E targetAnn = null;

        if (targetClass != null) {
            targetAnn = targetClass.getDeclaredAnnotation(annotationClass);

            if (targetAnn == null) {
                return method.getAnnotation(annotationClass);
            }
        }

        var methodAnn = method.getAnnotation(annotationClass);

        if (methodAnn == null) {
            return targetAnn;
        }

        return methodAnn;
    }
}
