package ru.clevertec.checksystem.core.event;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.auth.MailAuthenticator;
import ru.clevertec.checksystem.core.common.event.IEmailSender;
import ru.clevertec.checksystem.core.data.Mail;
import ru.clevertec.checksystem.core.util.CollectionUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

@Component
public class EmailSender extends EventEmitter<Object> implements IEmailSender {

    @Override
    public void next(Mail mail) {
        sendMail(mail);
    }

    public void sendMail(Mail mail) {
        try {
            Transport.send(createMessage(mail));
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MimeMessage createMessage(Mail mail) throws IOException, MessagingException {
        var configProperties = getConfigProperties();

        var session = Session.getInstance(configProperties, new MailAuthenticator(configProperties));

        var message = new MimeMessage(session);
        message.setFrom(new InternetAddress(
                configProperties.getProperty(Constants.Properties.Config.Mail.USERNAME)));
        message.setRecipients(Message.RecipientType.TO, mail.getToAddresses().toArray(InternetAddress[]::new));
        message.setSubject(mail.getSubject());
        message.setContent(createMessageContent(mail));

        return message;
    }

    private Multipart createMessageContent(Mail mail) throws MessagingException, IOException {

        var multipart = new MimeMultipart();

        var textPart = new MimeBodyPart();
        textPart.setContent(mail.getBody(), "text/html; charset=utf-8");

        multipart.addBodyPart(textPart);

        if (!CollectionUtils.isNullOrEmpty(mail.getFileAttachments())) {
            for (var attachment : mail.getFileAttachments()) {
                var attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(attachment);
                multipart.addBodyPart(attachmentPart);
            }
        }

        return multipart;
    }

    private Properties getConfigProperties() throws IOException {
        var configStream = EmailSender.class.getClassLoader()
                .getResourceAsStream(Constants.Properties.Config.FILENAME);

        var properties = new Properties();
        properties.load(configStream);

        return properties;
    }
}
