package ru.clevertec.checksystem.core.common.builder.discount.checkitem;

public interface IThresholdPercentageCheckItemDiscountBuilder
        extends IPercentageCheckItemDiscountBuilder<IThresholdPercentageCheckItemDiscountBuilder> {

    IThresholdPercentageCheckItemDiscountBuilder setThreshold(Long threshold);
}
