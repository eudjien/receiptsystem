package ru.clevertec.checksystem.core.event;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailSendListener implements IMailSendListener {

    private final JavaMailSender mailSender;

    @Override
    public void next(MimeMessage message) {
        ThrowUtils.Argument.nullValue("message", message);
        mailSender.send(message);
    }
}
