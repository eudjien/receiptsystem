package ru.clevertec.checksystem.core.io.writer.factory;

import ru.clevertec.checksystem.core.io.writer.CheckWriter;

public abstract class WriterCreator {
    public abstract CheckWriter create(String format) throws Exception;
}
