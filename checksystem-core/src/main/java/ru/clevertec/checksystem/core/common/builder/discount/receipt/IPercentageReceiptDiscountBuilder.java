package ru.clevertec.checksystem.core.common.builder.discount.receipt;

public interface IPercentageReceiptDiscountBuilder extends IReceiptDiscountBuilder<IPercentageReceiptDiscountBuilder> {

    IPercentageReceiptDiscountBuilder setPercent(Double percent);
}
