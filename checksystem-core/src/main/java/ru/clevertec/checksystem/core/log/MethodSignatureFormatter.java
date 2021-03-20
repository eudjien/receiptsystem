package ru.clevertec.checksystem.core.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodSignatureFormatter implements IMethodSignatureFormatter {

    private final static String DIFFERENT_TYPES_MESSAGE =
            "Argument '%s' can only be of the same type as the return type of the method (Method type: %s, Return type: %s)";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String format(String format, Method method) {
        return format(format, method, null);
    }

    @Override
    public String format(String format, Method method, Object[] args) {
        return format(format, method, args, null);
    }

    @Override
    public String format(String format, Method method, Object[] args, Object returnedData) {

        ThrowUtils.Argument.nullValue("format", method);

        if (returnedData != null && !method.getReturnType().isAssignableFrom(returnedData.getClass()))
            throw new IllegalArgumentException(String.format(DIFFERENT_TYPES_MESSAGE,
                    "returnedData", method.getReturnType().getName(), returnedData.getClass().getName()));

        return createMessage(format, method, args, returnedData);
    }

    private String createMessage(String format, Method method, Object[] args, Object returnedData) {

        return Pattern.compile("%\\w+").matcher(format).replaceAll(mr -> {

            try {
                switch (mr.group()) {
                    case MethodSignatureFormats.ReturnType -> {
                        return Matcher.quoteReplacement(method.getReturnType().getName());
                    }
                    case MethodSignatureFormats.MethodName -> {
                        return Matcher.quoteReplacement(method.getName());
                    }
                    case MethodSignatureFormats.ArgumentsTypes -> {
                        return Matcher.quoteReplacement(createFunctionArgumentNames(method.getParameters()));
                    }
                    case MethodSignatureFormats.ArgumentsData -> {
                        return Matcher.quoteReplacement(createFunctionArgumentData(args));
                    }
                    case MethodSignatureFormats.ReturnData -> {
                        return Matcher.quoteReplacement(createFunctionReturnData(method, returnedData));
                    }
                }
            } catch (JsonProcessingException ignored) {
            }
            return Matcher.quoteReplacement(mr.group());
        });
    }

    private String createFunctionReturnData(Method method, Object returnData) throws JsonProcessingException {
        return method.getReturnType().isAssignableFrom(Void.TYPE)
                ? "VOID"
                : objectMapper.writeValueAsString(returnData);
    }

    private static String createFunctionArgumentNames(Parameter[] parameters) {
        return Arrays.stream(parameters)
                .map(parameter -> parameter.getParameterizedType().getTypeName())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    private String createFunctionArgumentData(Object[] args) throws JsonProcessingException {
        return objectMapper.writeValueAsString(args);
    }
}
