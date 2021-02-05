package ru.clevertec.checksystem.core.common.log;

import java.lang.reflect.Method;

public interface IMethodLogger {

    void log(String level, String format, Method method);

    void log(String level, String format, Method method, Object[] args);

    void log(String level, String format, Method method, Object[] args, Object returnedData);
}
