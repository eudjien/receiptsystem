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

    void sendReceiptEmail(String subject, String address, Collection<Receipt> receipts, String type, String format)
            throws IOException;

    void sendEmail(String subject, Object body, String address, String contentType) throws IOException;

    void sendEmail(String subject, Object body, String address, String contentType, Set<File> attachments);

    EmailRepository getEmailRepository();

    EventEmailRepository getEventEmailRepository();
}
