package ru.clevertec.checksystem.core.entity;

import ru.clevertec.checksystem.core.constant.Entities;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(
        name = Entities.Table.EMAILS,
        indexes = @Index(columnList = Entities.Column.ADDRESS, unique = true)
)
public class Email extends BaseEntity {

    @Column(name = Entities.Column.ADDRESS, nullable = false)
    private String address;

    public Email() {
    }

    public Email(String address) throws AddressException {
        setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws AddressException {
        this.address = new InternetAddress(address).getAddress();
    }

    public InternetAddress toInternetAddress() {
        try {
            return new InternetAddress(getAddress());
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }
}
