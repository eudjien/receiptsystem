package ru.clevertec.checksystem.web;

public class CheckPrintDto {

    private int id;
    private String data;

    public CheckPrintDto() {
    }

    public CheckPrintDto(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
