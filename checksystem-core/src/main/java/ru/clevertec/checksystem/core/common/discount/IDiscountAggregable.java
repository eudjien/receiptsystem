package ru.clevertec.checksystem.core.common.discount;

import ru.clevertec.checksystem.core.entity.discount.Discount;
import ru.clevertec.checksystem.core.exception.ArgumentNullException;

import java.util.Collection;

public interface IDiscountAggregable<T extends Discount<T>> {

    Collection<T> getDiscounts();

    void setDiscounts(Collection<T> discounts) throws ArgumentNullException;

    void putDiscounts(Collection<T> discounts) throws ArgumentNullException;

    void putDiscount(T discount) throws ArgumentNullException;

    void removeDiscounts(Collection<T> discounts) throws ArgumentNullException;

    void removeDiscount(T discount) throws ArgumentNullException;
}
