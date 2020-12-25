package ru.clevertec.checksystem.core.io.printer;

import ru.clevertec.checksystem.core.io.printer.strategy.IPrintStrategy;

import java.util.List;

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

    public abstract List<PrintResult> print() throws Exception;
}
