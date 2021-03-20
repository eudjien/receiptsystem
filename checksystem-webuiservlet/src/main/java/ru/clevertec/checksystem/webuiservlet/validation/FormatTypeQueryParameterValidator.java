package ru.clevertec.checksystem.webuiservlet.validation;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.io.FormatType;
import ru.clevertec.checksystem.webuiservlet.constant.Parameters;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterIncorrectException;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterRequiredException;

import java.util.Map;

@Component
public class FormatTypeQueryParameterValidator extends QueryParameterValidator {

    @Override
    public void validate(Map<String, String[]> parameterMap) {

        var formatType = getParameter(parameterMap, Parameters.FORMAT_TYPE_PARAMETER);
        if (formatType == null)
            throw new QueryParameterRequiredException(Parameters.SOURCE_PARAMETER);

        try {
            FormatType.parse(formatType);
        } catch (IllegalArgumentException e) {
            throw new QueryParameterIncorrectException(Parameters.FORMAT_TYPE_PARAMETER);
        }
    }
}
