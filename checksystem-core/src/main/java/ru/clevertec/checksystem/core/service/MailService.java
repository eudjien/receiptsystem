package ru.clevertec.checksystem.core.service;

import com.google.common.net.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.IMailService;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.exception.EmailNotFoundException;
import ru.clevertec.checksystem.core.helper.FormatHelpers;
import ru.clevertec.checksystem.core.mail.Mail;
import ru.clevertec.checksystem.core.mail.MailAddress;
import ru.clevertec.checksystem.core.mail.MailSender;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static ru.clevertec.checksystem.core.Constants.Types;

@Service
public class MailService extends EventEmitter<Object> implements IMailService {

    private final MailSender mailSender;

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    private final IoReceiptService ioReceiptService;
    private final PrintingReceiptService printingService;

    @Autowired
    public MailService(
            MailSender mailSender,
            EmailRepository emailRepository,
            EventEmailRepository eventEmailRepository,
            IoReceiptService ioReceiptService,
            PrintingReceiptService printingService) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
        this.eventEmailRepository = eventEmailRepository;
        this.ioReceiptService = ioReceiptService;
        this.printingService = printingService;
    }

    public EventEmail assignEmailForEvent(String eventType, String address) {
        var email = emailRepository.findByAddress(address)
                .orElseThrow(() -> new EmailNotFoundException(address));
        return eventEmailRepository.save(new EventEmail(email, eventType));
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void sendReceiptEmail(
            String subject, Collection<Receipt> receipts, String type, String format, String... addresses) throws IOException {

        var tempFile = createTempFile(format);
        writeReceiptsToFile(receipts, tempFile, type, format);

        var htmlBody = printingService.printToHtml(receipts);

        var mail = new Mail(subject, htmlBody, MediaType.HTML_UTF_8, MailAddress.parseArray(addresses));
        mail.getAttachments().add(tempFile);

        mailSender.send(mail);
    }

    @Override
    public void sendEmail(String subject, Object body, String contentType, String... addresses) {
        sendEmail(subject, body, contentType, null, addresses);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void sendEmail(String subject, Object body, String contentType, Set<File> attachments, String... addresses) {

        var mail = new Mail(subject, body, MediaType.parse(contentType), MailAddress.parseArray(addresses));
        mail.setAttachments(attachments);

        mailSender.send(mail);
    }

    private static File createTempFile(String format) throws IOException {
        return File.createTempFile("receipts", FormatHelpers.extensionByFormat(format, true));
    }

    public boolean isValidEmailAddress(String emailAddress) {
        try {
            new InternetAddress(emailAddress);
            return true;
        } catch (AddressException ignored) {
            return false;
        }
    }

    private void writeReceiptsToFile(Collection<Receipt> receipts, File file, String type, String format) throws IOException {
        switch (type) {
            case Types.PRINT -> printingService.print(receipts, file, format);
            case Types.SERIALIZE -> ioReceiptService.serialize(receipts, file, format);
        }
    }

    public EmailRepository getEmailRepository() {
        return emailRepository;
    }

    public EventEmailRepository getEventEmailRepository() {
        return eventEmailRepository;
    }
}
