package ru.clevertec.checksystem.core.common;

import ru.clevertec.checksystem.core.io.print.layout.ILayout;

public interface ILayoutable<T> {
    ILayout<T> getLayout();

    void setLayout(ILayout<T> layout);
}
