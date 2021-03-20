package ru.clevertec.checksystem.core.common;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public interface IPrintable {
    byte[] print() throws IOException;

    void print(OutputStream os) throws IOException;

    void print(File destinationFile) throws IOException;
}
