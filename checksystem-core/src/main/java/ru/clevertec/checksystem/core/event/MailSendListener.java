package ru.clevertec.checksystem.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.mail.internet.MimeMessage;

@Component
public class MailSendListener implements IMailSendListener {

    private final JavaMailSender mailSender;

    @Autowired
    public MailSendListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void next(MimeMessage message) {
        ThrowUtils.Argument.nullValue("message", message);
        mailSender.send(message);
    }
}
