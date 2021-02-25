package ru.clevertec.checksystem.core.common.discount;

import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;

import java.util.Collection;

public interface IDiscountAggregable<T extends AbstractDiscount<T>> {

    Collection<T> getDiscounts();

    void setDiscounts(Collection<T> discounts);

    void addDiscount(T discount);

    void addDiscounts(Collection<T> discounts);

    void removeDiscount(T discount);

    void removeDiscounts(Collection<T> discounts);

    void clearDiscounts();
}
