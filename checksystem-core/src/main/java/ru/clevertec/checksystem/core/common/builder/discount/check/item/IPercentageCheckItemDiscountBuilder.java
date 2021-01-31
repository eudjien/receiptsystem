package ru.clevertec.checksystem.core.common.builder.discount.check.item;

public interface IPercentageCheckItemDiscountBuilder<T extends IPercentageCheckItemDiscountBuilder<T>>
        extends ICheckItemDiscountBuilder<T> {

    T setPercent(double percent);
}
