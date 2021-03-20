package ru.clevertec.checksystem.webuiservlet.validation;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.webuiservlet.constant.Parameters;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterRequiredException;

import java.util.Map;

@Component
public class MailSubjectQueryParameterValidator extends QueryParameterValidator {

    @Override
    public void validate(Map<String, String[]> parameterMap) {
        if (getParameter(parameterMap, Parameters.EMAIL_SUBJECT_PARAMETER) == null)
            throw new QueryParameterRequiredException(Parameters.EMAIL_SUBJECT_PARAMETER);
    }
}
