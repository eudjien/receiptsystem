package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.IEmailService;
import ru.clevertec.checksystem.core.data.Mail;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.event.EmailSender;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.exception.EmailNotFoundException;
import ru.clevertec.checksystem.core.factory.io.ReceiptPrinterFactory;
import ru.clevertec.checksystem.core.factory.io.ReceiptWriterFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;
import ru.clevertec.custom.list.SinglyLinkedList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.clevertec.checksystem.core.Constants.Format;
import static ru.clevertec.checksystem.core.Constants.Types;

@Service
public class EmailService extends EventEmitter<Object> implements IEmailService {

    private final EmailSender emailSender;

    private final ReceiptPrinterFactory receiptPrinterFactory;
    private final ReceiptWriterFactory receiptWriterFactory;

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public EmailService(
            EmailSender emailSender,
            ReceiptPrinterFactory receiptPrinterFactory,
            ReceiptWriterFactory receiptWriterFactory,
            EmailRepository emailRepository,
            EventEmailRepository eventEmailRepository) {
        this.emailSender = emailSender;
        this.receiptPrinterFactory = receiptPrinterFactory;
        this.receiptWriterFactory = receiptWriterFactory;
        this.emailRepository = emailRepository;
        this.eventEmailRepository = eventEmailRepository;
    }

    public EventEmail assignEmailForEvent(String eventType, String address) {
        var email = emailRepository.findByAddress(address)
                .orElseThrow(() -> new EmailNotFoundException(address));
        return eventEmailRepository.save(new EventEmail(email, eventType));
    }

    public void sendEmail(
            String subject, String address, Collection<Receipt> receipts, String type, String format) throws IOException, AddressException {

        var tempFile = createTempFile(type, format, receipts);

        var html = new String(receiptPrinterFactory.instance(Format.HTML, receipts).print(), StandardCharsets.UTF_8);

        switch (type) {
            case Types.PRINT -> receiptPrinterFactory.instance(format, receipts).print(tempFile);
            case Types.SERIALIZE -> receiptWriterFactory.instance(format).write(receipts, tempFile);
        }

        var addresses = Arrays.stream(InternetAddress.parse(address))
                .collect(Collectors.toCollection(SinglyLinkedList::new));
        var mail = new Mail(subject, html, addresses, tempFile);
        emailSender.sendMail(mail);
    }

    public File createTempFile(String type, String format, Collection<Receipt> receipts) throws IOException {

        var tempFile = File.createTempFile("receipts", FormatHelpers.extensionByFormat(format, true));

        try (var fos = new FileOutputStream(tempFile)) {
            switch (type) {
                case Types.PRINT -> receiptPrinterFactory.instance(format, receipts).print(fos);
                case Types.SERIALIZE -> receiptWriterFactory.instance(format).write(receipts, fos);
            }
        }

        return tempFile;
    }

    public boolean isValidEmailAddress(String emailAddress) {
        try {
            new InternetAddress(emailAddress);
            return true;
        } catch (AddressException ignored) {
            return false;
        }
    }

    public EmailRepository getEmailRepository() {
        return emailRepository;
    }

    public EventEmailRepository getEventEmailRepository() {
        return eventEmailRepository;
    }
}
