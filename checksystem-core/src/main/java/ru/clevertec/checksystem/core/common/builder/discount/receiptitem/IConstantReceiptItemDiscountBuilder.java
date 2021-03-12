package ru.clevertec.checksystem.core.common.builder.discount.receiptitem;

import java.math.BigDecimal;

public interface IConstantReceiptItemDiscountBuilder
        extends IReceiptItemDiscountBuilder<IConstantReceiptItemDiscountBuilder> {

    IConstantReceiptItemDiscountBuilder setConstant(BigDecimal constant);
}
