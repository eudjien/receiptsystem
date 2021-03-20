package ru.clevertec.checksystem.webuiservlet.validation;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.webuiservlet.constant.Parameters;
import ru.clevertec.checksystem.webuiservlet.constant.Sources;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterIncorrectException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class SourceQueryParameterValidator extends QueryParameterValidator {

    private final static Set<String> knownSources = new HashSet<>();

    static {
        knownSources.add("");
        knownSources.add(null);
        knownSources.add(Sources.FILE);
        knownSources.add(Sources.DATABASE);
    }

    @Override
    public void validate(Map<String, String[]> parameterMap) {

        var source = getParameter(parameterMap, Parameters.SOURCE_PARAMETER);

        if (!knownSources.contains(source))
            throw new QueryParameterIncorrectException(Parameters.SOURCE_PARAMETER);
    }
}
