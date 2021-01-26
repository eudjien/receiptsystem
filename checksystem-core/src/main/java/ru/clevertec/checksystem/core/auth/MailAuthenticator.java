package ru.clevertec.checksystem.core.auth;

import ru.clevertec.checksystem.core.Constants;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

public class MailAuthenticator extends Authenticator {

    private final Properties properties;

    public MailAuthenticator(Properties properties) throws IllegalArgumentException {
        if (properties == null) {
            throw new IllegalArgumentException("Argument 'properties' cannot be null");
        }
        this.properties = properties;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
                properties.getProperty(Constants.Properties.Config.Mail.USERNAME),
                properties.getProperty(Constants.Properties.Config.Mail.PASSWORD));
    }
}
