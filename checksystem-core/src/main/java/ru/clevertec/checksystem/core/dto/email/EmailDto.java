package ru.clevertec.checksystem.core.dto.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailDto {

    private Long id = 0L;

    @NotNull
    @Email
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
