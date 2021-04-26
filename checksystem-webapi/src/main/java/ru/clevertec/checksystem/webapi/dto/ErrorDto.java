package ru.clevertec.checksystem.webapi.dto;

import lombok.Value;

import java.util.Set;

@Value
public class ErrorDto {
    Long timestamp;
    Integer status;
    String error;
    Set<String> messages;
}
