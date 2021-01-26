package ru.clevertec.checksystem.core.common.builder.discount.check.item;

public interface IThresholdPercentageCheckItemDiscountBuilder
        extends IPercentageCheckItemDiscountBuilder<IThresholdPercentageCheckItemDiscountBuilder> {

    IThresholdPercentageCheckItemDiscountBuilder setThreshold(int threshold);
}
