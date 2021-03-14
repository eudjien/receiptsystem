package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public interface IMailService extends IService {

    EventEmail assignEmailForEvent(String eventType, String address);

    void sendReceiptEmail(String subject, Collection<Receipt> receipts, String type, String format, String... addresses)
            throws IOException;

    void sendEmail(String subject, Object body, String contentType, String... address) throws IOException;

    void sendEmail(String subject, Object body, String contentType, Set<File> attachments, String... addresses);

    EmailRepository getEmailRepository();

    EventEmailRepository getEventEmailRepository();
}
