package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.dto.Mail;
import ru.clevertec.checksystem.core.common.event.IEventListener;

public interface IMailService extends IService, IEventListener<Mail> {

     void sendMail(Mail mail);
}
