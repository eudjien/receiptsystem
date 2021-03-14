package ru.clevertec.checksystem.core.common.event;

import ru.clevertec.checksystem.core.mail.Mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IMailSender extends IEventEmitter<Object>, IEventListener<Mail> {

    void send(Mail mail) throws IOException, MessagingException;
}
