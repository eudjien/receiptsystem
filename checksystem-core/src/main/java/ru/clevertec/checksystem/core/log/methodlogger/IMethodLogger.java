package ru.clevertec.checksystem.core.log.methodlogger;

import java.lang.reflect.Method;

public interface IMethodLogger {

    void log(String level, String format, Method method);

    void log(String level, String format, Method method, Object[] args);

    void log(String level, String format, Method method, Object[] args, Object returnedData);
}
