package ru.clevertec.checksystem.core.common.io.read;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface IReader<TOutput> {

    Collection<TOutput> read(byte[] bytes) throws IOException;

    Collection<TOutput> read(InputStream inputStream) throws IOException;

    Collection<TOutput> read(File sourceFile) throws IOException;
}
