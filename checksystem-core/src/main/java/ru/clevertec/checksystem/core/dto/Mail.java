package ru.clevertec.checksystem.core.dto;

import javax.mail.internet.InternetAddress;
import java.io.File;

public class Mail {

    private final String subject;
    private final Object body;
    private final File[] fileAttachments;
    private final InternetAddress[] toAddresses;

    public Mail(String subject, Object body, InternetAddress[] toAddresses, File... fileAttachments) {
        this.subject = subject;
        this.body = body;
        this.toAddresses = toAddresses;
        this.fileAttachments = fileAttachments;
    }

    public String getSubject() {
        return subject;
    }

    public Object getBody() {
        return body;
    }

    public InternetAddress[] getToAddresses() {
        return toAddresses;
    }

    public File[] getFileAttachments() {
        return fileAttachments;
    }
}
