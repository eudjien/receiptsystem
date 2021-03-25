package ru.clevertec.checksystem.webmvcjdbc.model;

import ru.clevertec.checksystem.webmvcjdbc.constant.Sources;
import ru.clevertec.checksystem.webmvcjdbc.validation.annotation.FormatTypeConstraint;
import ru.clevertec.checksystem.webmvcjdbc.validation.annotation.SourceConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class MailModel {

    @SourceConstraint
    private String source = Sources.DATABASE;

    @FormatTypeConstraint
    private String formatType;

    @NotEmpty
    private String subject;

    @Email
    private String email;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
