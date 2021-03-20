package ru.clevertec.checksystem.webuiservlet.validation;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.webuiservlet.constant.Parameters;

@Component
public class ParameterValidatorFactory {

    private final ApplicationContext applicationContext;

    public ParameterValidatorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public QueryParameterValidator instance(String parameterName) {
        return switch (parameterName) {
            case Parameters.SOURCE_PARAMETER -> applicationContext.getBean(SourceQueryParameterValidator.class);
            case Parameters.FORMAT_TYPE_PARAMETER -> applicationContext.getBean(FormatTypeQueryParameterValidator.class);
            case Parameters.EMAIL_SUBJECT_PARAMETER -> applicationContext.getBean(MailSubjectQueryParameterValidator.class);
            case Parameters.EMAIL_ADDRESS_PARAMETER -> applicationContext.getBean(MailAddressQueryParameterValidator.class);
            default -> throw new IllegalArgumentException();
        };
    }
}
