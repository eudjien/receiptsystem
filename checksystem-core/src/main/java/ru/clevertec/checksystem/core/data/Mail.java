package ru.clevertec.checksystem.core.data;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class Mail {

    private String subject;
    private Object body;
    private Collection<InternetAddress> toAddresses;
    private Collection<File> fileAttachments;

    public Mail(String subject, Object body, Collection<InternetAddress> toAddresses, File... fileAttachments) {
        setSubject(subject);
        setBody(body);
        setToAddresses(toAddresses);
        if (fileAttachments != null)
            setFileAttachments(Arrays.asList(fileAttachments));
    }

    public String getSubject() {
        return subject;
    }

    public Object getBody() {
        return body;
    }

    public Collection<InternetAddress> getToAddresses() {
        return toAddresses;
    }

    public Collection<File> getFileAttachments() {
        return fileAttachments;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setFileAttachments(Collection<File> fileAttachments) {
        this.fileAttachments = fileAttachments;
    }

    public void setToAddresses(Collection<InternetAddress> toAddresses) {
        this.toAddresses = toAddresses;
    }
}
