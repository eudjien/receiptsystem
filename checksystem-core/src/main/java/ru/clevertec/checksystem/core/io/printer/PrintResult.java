package ru.clevertec.checksystem.core.io.printer;

public class PrintResult {

    private int id;
    private byte[] data;

    public PrintResult(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
