package ru.clevertec.checksystem.core.io.print;

import ru.clevertec.checksystem.core.io.print.layout.ILayout;

public abstract class AbstractPrinter<T> implements IPrinter<T> {

    private ILayout<T> layout;

    public AbstractPrinter() {
    }

    public AbstractPrinter(ILayout<T> layout) {
        setLayout(layout);
    }

    @Override
    public ILayout<T> getLayout() {
        return layout;
    }

    @Override
    public void setLayout(ILayout<T> layout) {
        this.layout = layout;
    }
}
