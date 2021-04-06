package ru.clevertec.checksystem.core.dto.email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EventEmailDto {

    private Long id = 0L;

    @NotBlank
    private String eventType;

    @NotNull
    private Long emailId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }
}
