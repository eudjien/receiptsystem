package ru.clevertec.checksystem.core.log.methodlogger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.execution.BeforeExecutionLog;
import ru.clevertec.checksystem.normalino.json.NormalinoJSON;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodLogger implements IMethodLogger {

    private final Logger logger;
    private static final HashMap<Class<?>, MethodLogger> instances = new HashMap<>();

    private MethodLogger(Logger logger) {
        this.logger = logger;
    }

    public static MethodLogger instance(Class<?> c) {

        if (c == null) {
            throw new IllegalArgumentException("Parameter 'c' cannot be null");
        }

        if (instances.containsKey(c)) {
            return instances.get(c);
        } else {
            var newInstance = new MethodLogger(LogManager.getLogger(c));
            instances.put(c, newInstance);
            return newInstance;
        }
    }

    @Override
    public void log(String level, String format, Method method) {
        log(level, format, method,null);
    }

    @Override
    public void log(String level, String format, Method method, Object[] args) {
        log(level, format, method, args, null);
    }

    @Override
    public void log(String level, String format, Method method, Object[] args, Object returnedData) {

        if (level.equals(LogLevel.NONE)) {
            return;
        }

        if (method == null) {
            throw new IllegalArgumentException("Argument 'format' cannot be null");
        }

        if (returnedData != null && method.getReturnType() != returnedData.getClass()) {
            throw new IllegalArgumentException(
                    "Argument 'returnedData' can only be of the same type as the return type of the method");
        }

        var message = createMessage(format, method, args, returnedData);
        log(level, message);
    }

    private void log(String level, String message) {
        switch (level) {
            case LogLevel.INFO -> logger.info(message);
            case LogLevel.DEBUG -> logger.debug(message);
            case LogLevel.TRACE -> logger.trace(message);
            case LogLevel.ERROR -> logger.error(message);
            default -> throw new IllegalArgumentException("Argument 'level' contains not supported value");
        }
    }

    private static String createMessage(String format, Method method, Object[] args, Object returnedData) {

        return Pattern.compile("%\\w+").matcher(format).replaceAll(mr -> {

            switch (mr.group()) {
                case MethodLoggerFormats.ReturnType -> {
                    return Matcher.quoteReplacement(method.getReturnType().getName());
                }
                case MethodLoggerFormats.MethodName -> {
                    return Matcher.quoteReplacement(method.getName());
                }
                case MethodLoggerFormats.ArgumentsTypes -> {
                    return Matcher.quoteReplacement(createArgsName(method.getParameters()));
                }
                case MethodLoggerFormats.ArgumentsData -> {
                    try {
                        return Matcher.quoteReplacement(createArgsData(args));
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                case MethodLoggerFormats.ReturnData -> {
                    try {
                        return Matcher.quoteReplacement(createResult(method, returnedData));
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            return Matcher.quoteReplacement(mr.group());
        });
    }

    private static String createResult(Method method, Object result)
            throws InvocationTargetException, IllegalAccessException {
        return method.getReturnType().isAssignableFrom(Void.TYPE)
                ? "VOID"
                : NormalinoJSON.toJsonString(result, false);
    }

    private static String createArgsName(Parameter[] parameters) {
        return Arrays.stream(parameters).
                map(Parameter::getParameterizedType)
                .map(Type::getTypeName)
                .reduce((a, b) -> a + ", " + b).orElse("");
    }

    private static String createArgsData(Object[] args) throws InvocationTargetException, IllegalAccessException {
        return NormalinoJSON.toJsonString(args, false);
    }
}
