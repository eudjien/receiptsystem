package ru.clevertec.checksystem.core.common;

public interface IEmittable<T> {

    void emit(String eventType, T value);
}
