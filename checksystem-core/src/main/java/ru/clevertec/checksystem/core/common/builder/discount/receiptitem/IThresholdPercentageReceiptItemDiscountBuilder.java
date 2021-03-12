package ru.clevertec.checksystem.core.common.builder.discount.receiptitem;

public interface IThresholdPercentageReceiptItemDiscountBuilder
        extends IPercentageReceiptItemDiscountBuilder<IThresholdPercentageReceiptItemDiscountBuilder> {

    IThresholdPercentageReceiptItemDiscountBuilder setThreshold(Long threshold);
}
