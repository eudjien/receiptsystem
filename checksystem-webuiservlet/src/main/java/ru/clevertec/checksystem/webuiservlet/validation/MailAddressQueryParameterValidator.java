package ru.clevertec.checksystem.webuiservlet.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.service.IMailService;
import ru.clevertec.checksystem.webuiservlet.constant.Parameters;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterIncorrectException;
import ru.clevertec.checksystem.webuiservlet.exception.QueryParameterRequiredException;

import java.util.Map;

@Component
public class MailAddressQueryParameterValidator extends QueryParameterValidator {

    private final IMailService mailService;

    @Autowired
    public MailAddressQueryParameterValidator(IMailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void validate(Map<String, String[]> parameterMap) {

        var mailAddress = getParameter(parameterMap, Parameters.EMAIL_ADDRESS_PARAMETER);

        if (mailAddress == null)
            throw new QueryParameterRequiredException(Parameters.EMAIL_ADDRESS_PARAMETER);

        if (!mailService.isValidEmailAddress(mailAddress))
            throw new QueryParameterIncorrectException(Parameters.EMAIL_ADDRESS_PARAMETER);
    }
}
