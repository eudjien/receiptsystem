package ru.clevertec.checksystem.core.factory.writer;

import ru.clevertec.checksystem.core.io.writer.CheckWriter;

public abstract class WriterCreator {
    public abstract CheckWriter create(String format) throws Exception;
}
