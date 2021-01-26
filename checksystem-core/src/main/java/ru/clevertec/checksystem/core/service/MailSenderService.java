package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.dto.Mail;
import ru.clevertec.checksystem.core.auth.MailAuthenticator;
import ru.clevertec.checksystem.core.common.service.IMailService;
import ru.clevertec.checksystem.core.event.EventEmitter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class MailSenderService extends EventEmitter<Object> implements IMailService {

    @Override
    public void next(Mail mail) {
        sendMail(mail);
    }

    public void sendMail(Mail mail) {
        try {
            var message = createMessage(mail);
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private MimeMessage createMessage(Mail mail) throws IOException, MessagingException {
        var configProperties = getConfigProperties();

        var session = Session.getInstance(configProperties, new MailAuthenticator(configProperties));

        var message = new MimeMessage(session);
        message.setFrom(new InternetAddress(
                configProperties.getProperty(Constants.Properties.Config.Mail.USERNAME)));
        message.setRecipients(Message.RecipientType.TO, mail.getToAddresses());
        message.setSubject(mail.getSubject());
        message.setContent(createContent(mail));

        return message;
    }

    private Multipart createContent(Mail mail) throws MessagingException, IOException {

        var multipart = new MimeMultipart();

        var textPart = new MimeBodyPart();
        textPart.setContent(mail.getBody(), "text/html; charset=utf-8");

        multipart.addBodyPart(textPart);

        if (mail.getFileAttachments() != null && mail.getFileAttachments().length > 0) {

            for (var attachment : mail.getFileAttachments()) {
                var attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(attachment);
                multipart.addBodyPart(attachmentPart);
            }
        }

        return multipart;
    }

    private Properties getConfigProperties() throws IOException {
        var configInputStream =
                MailSenderService.class.getClassLoader().getResourceAsStream(Constants.Properties.Config.FILENAME);

        var properties = new Properties();
        properties.load(configInputStream);

        return properties;
    }
}
