package ru.clevertec.checksystem.webapi.dto;

import java.util.Set;

public class ErrorDto {

    private final Long timestamp;
    private final Integer status;
    private final String error;
    private final Set<String> messages;

    public ErrorDto(Long timestamp, Integer status, String error, Set<String> messages) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.messages = messages;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Set<String> getMessages() {
        return messages;
    }
}
