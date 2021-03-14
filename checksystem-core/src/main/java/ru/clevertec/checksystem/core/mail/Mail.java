package ru.clevertec.checksystem.core.mail;

import com.google.common.net.MediaType;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class Mail {

    private String subject;
    private Object body;
    private MediaType bodyMediaType;
    private Set<MailAddress> addresses = new HashSet<>();
    private Set<File> attachments = new HashSet<>();

    public Mail(String subject, Object body, MediaType bodyMediaType, MailAddress... addresses) {
        setSubject(subject);
        setBody(body);
        setBodyMediaType(bodyMediaType);
        setAddresses(Arrays.stream(addresses).collect(Collectors.toSet()));
    }

    public String getSubject() {
        return subject;
    }

    public Object getBody() {
        return body;
    }

    public Set<MailAddress> getAddresses() {
        return addresses;
    }

    public Set<File> getAttachments() {
        return attachments;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setAttachments(Set<File> attachments) {
        this.attachments = attachments;
        if (this.attachments == null)
            this.attachments = new HashSet<>();
    }

    public void setAddresses(Set<MailAddress> addresses) {
        this.addresses = addresses;
        if (this.addresses == null)
            this.addresses = new HashSet<>();
    }

    public MediaType getBodyContentType() {
        return bodyMediaType;
    }

    public void setBodyMediaType(MediaType bodyMediaType) {
        this.bodyMediaType = bodyMediaType;
    }
}
