package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.IEmailService;
import ru.clevertec.checksystem.core.data.Mail;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.event.EmailSender;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.exception.EmailNotFoundException;
import ru.clevertec.checksystem.core.factory.io.CheckPrinterFactory;
import ru.clevertec.checksystem.core.factory.io.CheckWriterFactory;
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

    private final CheckPrinterFactory checkPrinterFactory;
    private final CheckWriterFactory checkWriterFactory;

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public EmailService(
            EmailSender emailSender,
            CheckPrinterFactory checkPrinterFactory,
            CheckWriterFactory checkWriterFactory,
            EmailRepository emailRepository,
            EventEmailRepository eventEmailRepository) {
        this.emailSender = emailSender;
        this.checkPrinterFactory = checkPrinterFactory;
        this.checkWriterFactory = checkWriterFactory;
        this.emailRepository = emailRepository;
        this.eventEmailRepository = eventEmailRepository;
    }

    public EventEmail assignEmailForEvent(String eventType, String address) {
        var email = emailRepository.findByAddress(address)
                .orElseThrow(() -> new EmailNotFoundException(address));
        return eventEmailRepository.save(new EventEmail(email, eventType));
    }

    public void sendEmail(
            String subject, String address, Collection<Check> checks, String type, String format) throws IOException {

        var tempFile = createTempFile(type, format, checks);

        var html = new String(checkPrinterFactory.instance(Format.HTML, checks).print(), StandardCharsets.UTF_8);

        switch (type) {
            case Types.PRINT -> checkPrinterFactory.instance(format, checks).print(tempFile);
            case Types.SERIALIZE -> checkWriterFactory.instance(format).write(checks, tempFile);
        }

        try {
            var addresses = Arrays.stream(InternetAddress.parse(address))
                    .collect(Collectors.toCollection(SinglyLinkedList::new));
            var mail = new Mail(subject, html, addresses, tempFile);
            emailSender.sendMail(mail);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public File createTempFile(String type, String format, Collection<Check> checks) throws IOException {

        var tempFile = File.createTempFile("checks", FormatHelpers.extensionByFormat(format, true));

        try (var fos = new FileOutputStream(tempFile)) {
            switch (type) {
                case Types.PRINT -> checkPrinterFactory.instance(format, checks).print(fos);
                case Types.SERIALIZE -> checkWriterFactory.instance(format).write(checks, fos);
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
