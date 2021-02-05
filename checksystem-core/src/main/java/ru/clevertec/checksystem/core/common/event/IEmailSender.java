package ru.clevertec.checksystem.core.common.event;

import ru.clevertec.checksystem.core.data.Mail;

public interface IEmailSender extends IEventEmitter<Object>, IEventListener<Mail> {

    void sendMail(Mail mail);
}
