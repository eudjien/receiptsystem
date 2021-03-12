package ru.clevertec.checksystem.core.common.builder.discount.receiptitem;

public interface IPercentageReceiptItemDiscountBuilder<T extends IPercentageReceiptItemDiscountBuilder<T>>
        extends IReceiptItemDiscountBuilder<T> {

    T setPercent(Double percent);
}
