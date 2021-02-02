package ru.clevertec.checksystem.core.log.methodlogger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.normalino.json.NormalinoJSON;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodLogger implements IMethodLogger {

    private final static String DIFFERENT_TYPES_MESSAGE =
            "Argument '%s' can only be of the same type as the return type of the method (Method type: %s, Return type: %s)";

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
    public void log(String level, String format, Method method) throws ArgumentUnsupportedException {
        log(level, format, method, null);
    }

    @Override
    public void log(String level, String format, Method method, Object[] args) throws ArgumentUnsupportedException {
        log(level, format, method, args, null);
    }

    @Override
    public void log(String level, String format, Method method, Object[] args, Object returnedData) throws ArgumentUnsupportedException {

        if (level.equals(LogLevel.NONE)) {
            return;
        }

        ThrowUtils.Argument.theNull("format", method);

        if (returnedData != null && !method.getReturnType().isAssignableFrom(returnedData.getClass())) {
            throw new IllegalArgumentException(String.format(DIFFERENT_TYPES_MESSAGE,
                    "returnedData", method.getReturnType().getName(), returnedData.getClass().getName()));
        }

        var message = createMessage(format, method, args, returnedData);
        log(level, message);
    }

    private void log(String level, String message) throws ArgumentUnsupportedException {
        switch (level) {
            case LogLevel.INFO -> logger.info(message);
            case LogLevel.DEBUG -> logger.debug(message);
            case LogLevel.TRACE -> logger.trace(message);
            case LogLevel.ERROR -> logger.error(message);
            default -> throw new ArgumentUnsupportedException("level");
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
                    return Matcher.quoteReplacement(createArgsData(args));
                }
                case MethodLoggerFormats.ReturnData -> {
                    return Matcher.quoteReplacement(createResult(method, returnedData));
                }
            }

            return Matcher.quoteReplacement(mr.group());
        });
    }

    private static String createResult(Method method, Object result) {
        return method.getReturnType().isAssignableFrom(Void.TYPE)
                ? "VOID"
                : NormalinoJSON.stringify(result, false);
    }

    private static String createArgsName(Parameter[] parameters) {
        return Arrays.stream(parameters).
                map(Parameter::getParameterizedType)
                .map(Type::getTypeName)
                .reduce((a, b) -> a + ", " + b).orElse("");
    }

    private static String createArgsData(Object[] args) {
        return NormalinoJSON.stringify(args, false);
    }
}
