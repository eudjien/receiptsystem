package ru.clevertec.checksystem.core.service;

import com.google.common.net.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.IMailService;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.exception.EmailNotFoundException;
import ru.clevertec.checksystem.core.factory.io.ReceiptPrinterFactory;
import ru.clevertec.checksystem.core.factory.io.ReceiptWriterFactory;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.mail.Mail;
import ru.clevertec.checksystem.core.mail.MailAddress;
import ru.clevertec.checksystem.core.mail.MailSender;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;

import static ru.clevertec.checksystem.core.Constants.Formats;
import static ru.clevertec.checksystem.core.Constants.Types;

@Service
public class MailService extends EventEmitter<Object> implements IMailService {

    private final MailSender mailSender;

    private final ReceiptPrinterFactory receiptPrinterFactory;
    private final ReceiptWriterFactory receiptWriterFactory;

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public MailService(
            MailSender mailSender,
            ReceiptPrinterFactory receiptPrinterFactory,
            ReceiptWriterFactory receiptWriterFactory,
            EmailRepository emailRepository,
            EventEmailRepository eventEmailRepository) {
        this.mailSender = mailSender;
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

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void sendReceiptEmail(
            String subject, String address, Collection<Receipt> receipts, String type, String format) throws IOException {

        var tempFile = createTempFile(type, format, receipts);

        writeReceiptsToTempFile(receipts, tempFile, type, format);

        var htmlBody = new String(receiptPrinterFactory.instance(Formats.HTML, receipts).print(), StandardCharsets.UTF_8);

        var mail = new Mail(subject, htmlBody, new MailAddress(address));
        mail.getAttachments().add(tempFile);
        mail.setBodyMediaType(MediaType.HTML_UTF_8);

        mailSender.sendMail(mail);
    }


    @Override
    public void sendEmail(String subject, Object body, String address, String contentType) {
        sendEmail(subject, body, address, contentType, null);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void sendEmail(String subject, Object body, String address, String contentType, Set<File> attachments) {

        var mail = new Mail(subject, body, new MailAddress(address));
        mail.setBodyMediaType(MediaType.parse(contentType));
        mail.setAttachments(attachments);

        mailSender.sendMail(mail);
    }

    private File createTempFile(String type, String format, Collection<Receipt> receipts) throws IOException {

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

    private void writeReceiptsToTempFile(Collection<Receipt> receipts, File tempFile, String type, String format) throws IOException {
        switch (type) {
            case Types.PRINT -> receiptPrinterFactory.instance(format, receipts).print(tempFile);
            case Types.SERIALIZE -> receiptWriterFactory.instance(format).write(receipts, tempFile);
        }
    }

    public EmailRepository getEmailRepository() {
        return emailRepository;
    }

    public EventEmailRepository getEventEmailRepository() {
        return eventEmailRepository;
    }
}
