package ru.clevertec.checksystem.core.common.builder.discount.checkitem;

public interface IPercentageCheckItemDiscountBuilder<T extends IPercentageCheckItemDiscountBuilder<T>>
        extends ICheckItemDiscountBuilder<T> {

    T setPercent(Double percent);
}
