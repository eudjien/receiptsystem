package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.exception.EmailNotFoundException;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

@Service
public class MailService extends EventEmitter<Object> implements IMailService {

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public MailService(
            JavaMailSender mailSender,
            EmailRepository emailRepository,
            EventEmailRepository eventEmailRepository,
            ApplicationContext applicationContext) {
        super(applicationContext);
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
        this.eventEmailRepository = eventEmailRepository;
    }

    public EventEmail assignEmailForEvent(String eventType, String address) {
        var mail = emailRepository.findByAddress(address)
                .orElseThrow(() -> new EmailNotFoundException(address));
        return eventEmailRepository.save(new EventEmail(mail, eventType));
    }

    @Override
    public void sendTextMail(String subject, String body, String... addresses) throws MessagingException {
        sendMail(subject, body, false, null, addresses);
    }

    @Override
    public void sendTextMail(String subject, String body, Set<File> attachments, String... addresses) throws MessagingException {
        sendMail(subject, body, false, attachments, addresses);
    }

    @Override
    public void sendHtmlMail(String subject, String body, String... addresses) throws MessagingException {
        sendMail(subject, body, true, null, addresses);
    }

    @Override
    public void sendHtmlMail(String subject, String body, Set<File> attachments, String... addresses) throws MessagingException {
        sendMail(subject, body, true, attachments, addresses);
    }

    private void sendMail(String subject, String body, boolean isHtml, Set<File> attachments, String... addresses) throws MessagingException {
        var message = createMessage(subject, body, isHtml, attachments, addresses);
        mailSender.send(message);
    }

    public boolean isValidEmailAddress(String emailAddress) {
        try {
            new InternetAddress(emailAddress);
            return true;
        } catch (AddressException ignored) {
            return false;
        }
    }

    public MimeMessage createMessage(String subject, String body, boolean isHtml, Set<File> attachments, String... addresses) throws MessagingException {

        var mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(),
                attachments != null && attachments.size() > 0);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(addresses);
        mimeMessageHelper.setText(body, isHtml);

        if (attachments != null)
            for (var attachment : attachments)
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);

        return mimeMessageHelper.getMimeMessage();
    }
}
