package ru.clevertec.checksystem.core.common.discount;

import ru.clevertec.checksystem.core.entity.discount.Discount;

import java.util.Collection;

public interface IDiscountAggregable<T extends Discount<T>> {

    Collection<T> getDiscounts();

    void setDiscounts(Collection<T> discounts);

    void putDiscounts(Collection<T> discounts);

    void putDiscount(T discount);

    void removeDiscounts(Collection<T> discounts);

    void removeDiscount(T discount);

    void clearDiscounts();
}
