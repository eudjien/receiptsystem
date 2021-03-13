package ru.clevertec.checksystem.core.common.event;

import ru.clevertec.checksystem.core.mail.Mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IEmailSender extends IEventEmitter<Object>, IEventListener<Mail> {

    void sendMail(Mail mail) throws IOException, MessagingException;
}
