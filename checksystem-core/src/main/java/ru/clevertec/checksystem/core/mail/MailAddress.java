package ru.clevertec.checksystem.core.mail;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MailAddress extends Address {

    private final InternetAddress internetAddress;

    public MailAddress(String address) {
        this(address, null);
    }

    public MailAddress(String address, String personal) {
        try {
            this.internetAddress = InternetAddress.parse(address)[0];
            this.internetAddress.setPersonal(personal);
        } catch (UnsupportedEncodingException | AddressException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private MailAddress(InternetAddress internetAddress) {
        this.internetAddress = internetAddress;
    }

    public String getAddress() {
        return internetAddress.getAddress();
    }

    public String getPersonal() {
        return internetAddress.getPersonal();
    }

    @Override
    public String toString() {
        return internetAddress.toString();
    }

    @Override
    public int hashCode() {
        return internetAddress.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return internetAddress.equals(o);
    }

    @Override
    public Object clone() {
        return internetAddress.clone();
    }

    @Override
    public String getType() {
        return internetAddress.getType();
    }

    public static MailAddress[] parse(String addressList) {
        try {
            var internetAddresses = InternetAddress.parse(addressList);
            return Arrays.stream(internetAddresses).map(MailAddress::new).toArray(MailAddress[]::new);
        } catch (AddressException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
