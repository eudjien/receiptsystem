package ru.clevertec.checksystem.core.io.reader;

import ru.clevertec.checksystem.normalino.list.NormalinoList;

public abstract class Reader<TInput, TOutput> {
    public Reader() {
    }

    public abstract TOutput read(TInput input) throws Exception;

    public abstract NormalinoList<TOutput> readMany(TInput input) throws Exception;
}
