package ru.clevertec.checksystem.core.log.methodlogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface IMethodLogger {

    void signatureNames(String level, Method method)
            throws InvocationTargetException, IllegalAccessException;

    void signatureData(String level, Method method, Object[] args, Object returnedData)
            throws InvocationTargetException, IllegalAccessException;

    void argumentData(String level, Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException;

    void returnedData(String level, Method method, Object returnedData)
            throws InvocationTargetException, IllegalAccessException;

    void error(Method method, Object[] args, Throwable throwable)
            throws InvocationTargetException, IllegalAccessException;

    void log(String level, String format, Method method, Object[] args);

    void log(String level, String format, Method method, Object[] args, Object returnedData);
}
