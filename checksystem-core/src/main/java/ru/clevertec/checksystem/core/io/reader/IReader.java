package ru.clevertec.checksystem.core.io.reader;

import java.io.File;
import java.util.Collection;

public interface IReader<TOutput> {
    Collection<TOutput> read(byte[] bytes) throws Exception;

    Collection<TOutput> read(File file) throws Exception;
}
