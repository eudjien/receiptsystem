package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

import java.io.IOException;
import java.util.Collection;

public interface IEmailService extends IService {

    EventEmail assignEmailForEvent(String eventType, String address);

    void sendEmail(String subject, String address, Collection<Check> checks, String type, String format)
            throws IOException;

    EmailRepository getEmailRepository();

    EventEmailRepository getEventEmailRepository();
}
