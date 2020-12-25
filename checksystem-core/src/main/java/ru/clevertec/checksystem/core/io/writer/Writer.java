package ru.clevertec.checksystem.core.io.writer;

import java.util.List;

public abstract class Writer<TInput, TOutput> {

    public Writer() {
    }

    public abstract TOutput write(TInput input) throws Exception;

    public abstract TOutput write(List<TInput> input) throws Exception;
}
