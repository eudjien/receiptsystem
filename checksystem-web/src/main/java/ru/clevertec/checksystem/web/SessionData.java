package ru.clevertec.checksystem.web;

public class SessionData {

    private String format;
    private byte[] data;

    public SessionData(String format, byte[] data) {
        this.format = format;
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
