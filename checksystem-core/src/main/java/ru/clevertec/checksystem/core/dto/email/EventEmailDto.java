package ru.clevertec.checksystem.core.dto.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class EventEmailDto {

    private Long id;

    @NotBlank
    private String eventType;

    @NotNull
    private Long emailId;
}
