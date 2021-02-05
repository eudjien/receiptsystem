package ru.clevertec.checksystem.core.common.builder.discount.check;

public interface IPercentageCheckDiscountBuilder extends ICheckDiscountBuilder<IPercentageCheckDiscountBuilder> {

    IPercentageCheckDiscountBuilder setPercent(Double percent);
}
