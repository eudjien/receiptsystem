package ru.clevertec.checksystem.core.io.read;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface IReader<TOutput> {

    Collection<TOutput> read(byte[] bytes) throws IOException;

    Collection<TOutput> read(InputStream is) throws IOException;

    Collection<TOutput> read(File file) throws IOException;
}
