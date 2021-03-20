package ru.clevertec.checksystem.core.event;

public interface IEventListener<T> {
    void next(T value);
}
