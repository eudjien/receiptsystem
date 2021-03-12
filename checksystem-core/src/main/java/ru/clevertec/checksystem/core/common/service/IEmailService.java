package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.Collection;

public interface IEmailService extends IService {

    EventEmail assignEmailForEvent(String eventType, String address);

    void sendEmail(String subject, String address, Collection<Receipt> receipts, String type, String format)
            throws IOException, AddressException;

    EmailRepository getEmailRepository();

    EventEmailRepository getEventEmailRepository();
}
