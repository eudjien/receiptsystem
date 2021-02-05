package ru.clevertec.checksystem.core.common.io.write;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public interface IWriter<TInput> {

    byte[] write(Collection<TInput> input) throws IOException;

    void write(Collection<TInput> input, OutputStream outputStream) throws IOException;

    void write(Collection<TInput> input, File destinationFile) throws IOException;
}
