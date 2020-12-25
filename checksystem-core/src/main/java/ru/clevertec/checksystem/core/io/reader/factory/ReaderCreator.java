package ru.clevertec.checksystem.core.io.reader.factory;

import ru.clevertec.checksystem.core.io.reader.CheckReader;

public abstract class ReaderCreator {
    public abstract CheckReader create(String format) throws Exception;
}
