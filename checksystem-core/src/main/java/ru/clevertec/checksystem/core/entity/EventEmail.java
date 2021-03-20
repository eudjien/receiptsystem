package ru.clevertec.checksystem.core.entity;

import ru.clevertec.checksystem.core.constant.Entities;

import javax.persistence.*;

@Entity
@Table(
        name = Entities.Table.EVENT_EMAILS,
        indexes = @Index(columnList = Entities.JoinColumn.EMAIL_ID + "," +
                Entities.Column.EVENT_TYPE,
                unique = true)
)
public class EventEmail extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(
            name = Entities.JoinColumn.EMAIL_ID,
            referencedColumnName = Entities.Column.ID,
            nullable = false)
    private Email email;

    @Column(name = Entities.Column.EVENT_TYPE, nullable = false)
    private String eventType;

    @Column(name = Entities.JoinColumn.EMAIL_ID, insertable = false, updatable = false)
    private Long emailId;

    public EventEmail() {
    }

    public EventEmail(Email email, String eventType) {
        setEmail(email);
        setEventType(eventType);
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventName) {
        this.eventType = eventName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
