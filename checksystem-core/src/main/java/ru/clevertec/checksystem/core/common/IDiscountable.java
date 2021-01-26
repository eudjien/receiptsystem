package ru.clevertec.checksystem.core.common;

import ru.clevertec.checksystem.core.entity.discount.Discount;

import java.util.Collection;

public interface IDiscountable<T extends Discount<T>> {

    Collection<T> getDiscounts();

    void setDiscounts(Collection<T> discounts) throws IllegalArgumentException;

    void putDiscounts(Collection<T> discounts) throws IllegalArgumentException;

    void putDiscount(T discount) throws IllegalArgumentException;

    void removeDiscounts(Collection<T> discounts) throws IllegalArgumentException;

    void removeDiscount(T discount) throws IllegalArgumentException;
}
