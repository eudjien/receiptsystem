package ru.clevertec.checksystem.core.io.print;

import ru.clevertec.checksystem.core.io.print.strategy.IPrintStrategy;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public abstract class Printer<T> {

    private IPrintStrategy<T> strategy;

    public Printer() {
    }

    public Printer(IPrintStrategy<T> strategy) {
        setStrategy(strategy);
    }

    public IPrintStrategy<T> getStrategy() {
        return strategy;
    }

    public void setStrategy(IPrintStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public abstract Collection<PrintResult> print() throws IOException;

    public abstract byte[] printRaw() throws IOException;

    public abstract void printRaw(OutputStream outputStream) throws IOException;

    public abstract void printRaw(File destinationFile) throws IOException;
}
