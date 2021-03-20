package ru.clevertec.checksystem.core.log;

import java.lang.reflect.Method;

public interface IMethodSignatureFormatter {

    String format(String format, Method method);

    String format(String format, Method method, Object[] args);

    String format(String format, Method method, Object[] args, Object returnedData);
}
