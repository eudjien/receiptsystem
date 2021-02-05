package ru.clevertec.checksystem.core.common;

public interface IScalable<T extends Number> {
    T getScale();

    void setScale(T scale);
}
