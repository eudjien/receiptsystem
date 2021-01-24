package ru.clevertec.checksystem.core.io.writer;

import java.io.File;
import java.util.Collection;

public interface IWriter<TInput> {

    byte[] write(Collection<TInput> input) throws Exception;

    void write(Collection<TInput> input, File file) throws Exception;
}
