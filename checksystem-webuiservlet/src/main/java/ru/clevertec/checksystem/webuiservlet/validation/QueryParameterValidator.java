package ru.clevertec.checksystem.webuiservlet.validation;


import java.util.Map;

public abstract class QueryParameterValidator {

    public abstract void validate(Map<String, String[]> parameterMap);

    protected static String getParameter(Map<String, String[]> parameterMap, String parameterName) {

        if (!parameterMap.containsKey(parameterName))
            return null;

        var values = parameterMap.get(parameterName);

        if (values == null || values.length == 0)
            return null;

        return values[0];
    }
}
