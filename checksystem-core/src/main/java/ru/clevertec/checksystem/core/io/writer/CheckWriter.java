package ru.clevertec.checksystem.core.io.writer;

import ru.clevertec.checksystem.core.check.Check;

public abstract class CheckWriter extends Writer<Check, byte[]> {
    public CheckWriter() {
    }
}
