package ru.clevertec.checksystem.core.common.builder.discount;

import ru.clevertec.checksystem.core.common.IBuildable;


public interface IDiscountBuilder<T, R extends IDiscountBuilder<T, R>> extends IBuildable<T> {

    R setId(Long id);

    R setDescription(String description);

    R setDependentDiscount(T discount);
}
