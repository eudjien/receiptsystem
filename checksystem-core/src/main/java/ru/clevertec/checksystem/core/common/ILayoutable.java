package ru.clevertec.checksystem.core.common;

public interface ILayoutable<T> {
    ILayout<T> getLayout();

    void setLayout(ILayout<T> layout);
}
