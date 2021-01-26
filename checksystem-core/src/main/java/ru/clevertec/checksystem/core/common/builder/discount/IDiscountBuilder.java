package ru.clevertec.checksystem.core.common.builder.discount;

import ru.clevertec.checksystem.core.common.IBuildable;

public interface IDiscountBuilder<T, R extends IDiscountBuilder<T, R>> extends IBuildable<T> {

    R setId(int id) throws IllegalArgumentException;

    R setDescription(String description) throws IllegalArgumentException;

    R setChildDiscount(T discount) throws IllegalArgumentException;
}
