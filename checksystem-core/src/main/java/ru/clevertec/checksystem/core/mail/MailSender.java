package ru.clevertec.checksystem.core.mail;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.auth.MailAuthenticator;
import ru.clevertec.checksystem.core.common.event.IMailSender;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

@Component
public class MailSender extends EventEmitter<Object> implements IMailSender {

    private static final Properties configProperties = new Properties();

    public MailSender() throws IOException {
        loadConfigProperties();
    }

    @Override
    public void next(Mail mail) {
        sendMail(mail);
    }

    @Override
    public void send(Mail mail) {
        sendMail(mail);
    }

    private static void sendMail(Mail mail) {
        ThrowUtils.Argument.nullValue("mail", mail);
        try {
            Transport.send(createMessage(mail));
        } catch (MessagingException | IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static Message createMessage(Mail mail) throws IOException, MessagingException {

        var session = Session.getInstance(configProperties, new MailAuthenticator(configProperties));

        var message = new MimeMessage(session);
        message.setFrom(new InternetAddress(
                configProperties.getProperty(Constants.Properties.Config.Mail.USERNAME)));
        message.setRecipients(Message.RecipientType.TO, mail.getAddresses().toArray(MailAddress[]::new));
        message.setSubject(mail.getSubject());
        message.setContent(createMessageContent(mail));

        return message;
    }

    @SuppressWarnings("UnstableApiUsage")
    private static Multipart createMessageContent(Mail mail) throws MessagingException, IOException {

        var multipart = new MimeMultipart();

        var contentPart = new MimeBodyPart();
        contentPart.setContent(mail.getBody(), mail.getBodyContentType().toString());

        multipart.addBodyPart(contentPart);

        for (var attachment : mail.getAttachments()) {
            var attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);
            multipart.addBodyPart(attachmentPart);
        }

        return multipart;
    }

    private static void loadConfigProperties() throws IOException {

        var configStream = MailSender.class.getClassLoader()
                .getResourceAsStream(Constants.Properties.Config.FILENAME);

        MailSender.configProperties.load(configStream);
    }
}
