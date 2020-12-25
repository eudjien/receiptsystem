package ru.clevertec.checksystem.core.io.reader;

import java.util.List;

public abstract class Reader<TInput, TOutput> {
    public Reader() {
    }

    public abstract TOutput read(TInput input) throws Exception;

    public abstract List<TOutput> readMany(TInput input) throws Exception;
}
