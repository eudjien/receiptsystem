package ru.clevertec.checksystem.core.common;

public interface IBuildable<T> {

    T build() throws IllegalArgumentException;
}
