package ru.clevertec.checksystem.core.common.event;

public interface IEventListener<T> {
    void next(T value);
}
