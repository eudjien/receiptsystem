package ru.clevertec.checksystem.core.factory.reader;

import ru.clevertec.checksystem.core.io.reader.CheckReader;

public abstract class ReaderCreator {
    public abstract CheckReader create(String format) throws Exception;
}
