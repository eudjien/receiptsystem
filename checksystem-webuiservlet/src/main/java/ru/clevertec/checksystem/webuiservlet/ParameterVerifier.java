package ru.clevertec.checksystem.webuiservlet;

import ru.clevertec.checksystem.core.util.ThrowUtils;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterIncorrectException;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterRequiredException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static ru.clevertec.checksystem.core.Constants.Formats;
import static ru.clevertec.checksystem.core.Constants.Types;
import static ru.clevertec.checksystem.webuiservlet.Constants.Parameters;
import static ru.clevertec.checksystem.webuiservlet.Constants.Sources;

public class ParameterVerifier {

    private static final Map<String, Set<String>> parameterValuesMap = new HashMap<>();

    static {
        addParameterEntry(Parameters.TYPE_PARAMETER, Types.PRINT, Types.SERIALIZE);
        addParameterEntry(Parameters.SOURCE_PARAMETER, null, "", Sources.FILE, Sources.DATABASE);
        addParameterEntry(Parameters.FORMAT_PARAMETER, Formats.HTML, Formats.PDF, Formats.TEXT, Formats.JSON, Formats.XML);
    }

    public void verifyForRequired(HttpServletRequest request, String... parameterNames) {
        for (var parameterName : parameterNames)
            if (request.getParameter(parameterName) == null)
                throw new QueryParameterRequiredException(parameterName);
    }

    public void verifyForSuitable(HttpServletRequest request, String... parameterNames) {
        for (var parameterName : parameterNames) {
            var parameterValue = request.getParameter(parameterName);
            if (!parameterValuesMap.get(parameterName).contains(parameterValue))
                throw new QueryParameterIncorrectException(parameterName);
        }
    }

    public void verifyForKnown(String parameterName) {
        if (!parameterValuesMap.containsKey(parameterName))
            throw new QueryParameterRequiredException(parameterName);
    }

    public void verifyForKnownAndSuitable(String parameterName, String parameterValue) {
        verifyForKnown(parameterName);
        if (!parameterValuesMap.get(parameterName).contains(parameterValue))
            throw new QueryParameterIncorrectException(parameterName);
    }

    private static void addParameterEntry(String parameterName, String... parameterValues) {
        ThrowUtils.Argument.nullOrBlank("parameterName", parameterName);
        parameterValuesMap.put(parameterName, new HashSet<>(Arrays.asList(parameterValues)));
    }
}
