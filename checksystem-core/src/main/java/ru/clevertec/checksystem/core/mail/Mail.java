package ru.clevertec.checksystem.core.mail;

import com.google.common.net.MediaType;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class Mail {

    private String subject;
    private Object body;
    private MediaType bodyMediaType = MediaType.PLAIN_TEXT_UTF_8;
    private Set<MailAddress> addresses;
    private Set<File> attachments = new HashSet<>();

    public Mail(String subject, Object body, MediaType bodyMediaType, MailAddress... addresses) {
        this(subject, body, addresses);
        setBodyMediaType(bodyMediaType);
    }

    public Mail(String subject, Object body, MailAddress... addresses) {
        setSubject(subject);
        setBody(body);
        setAddresses(Arrays.stream(addresses).collect(Collectors.toSet()));
    }

    public String getSubject() {
        return subject;
    }

    public Object getBody() {
        return body;
    }

    public Collection<MailAddress> getAddresses() {
        return addresses;
    }

    public Collection<File> getAttachments() {
        return attachments;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setAttachments(Set<File> attachments) {
        this.attachments.clear();
        if (attachments != null)
            this.attachments = attachments;
    }

    public void setAddresses(Set<MailAddress> addresses) {
        this.addresses = addresses;
    }

    public MediaType getBodyContentType() {
        return bodyMediaType;
    }

    public void setBodyMediaType(MediaType bodyMediaType) {
        this.bodyMediaType = bodyMediaType;
    }
}
