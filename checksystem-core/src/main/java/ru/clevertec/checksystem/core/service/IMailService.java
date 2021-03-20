package ru.clevertec.checksystem.core.service;

import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.event.IEventEmitter;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Set;

public interface IMailService extends IEventEmitter<Object> {

    EventEmail assignEmailForEvent(String eventType, String address);

    void sendTextMail(String subject, String body, String... addresses) throws MessagingException;

    void sendTextMail(String subject, String body, Set<File> attachments, String... addresses) throws MessagingException;

    void sendHtmlMail(String subject, String body, String... addresses) throws MessagingException;

    void sendHtmlMail(String subject, String body, Set<File> attachments, String... addresses) throws MessagingException;

    boolean isValidEmailAddress(String emailAddress);
}
