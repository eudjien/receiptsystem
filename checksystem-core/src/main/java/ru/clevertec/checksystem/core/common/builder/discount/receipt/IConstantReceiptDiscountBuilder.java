package ru.clevertec.checksystem.core.common.builder.discount.receipt;

import java.math.BigDecimal;

public interface IConstantReceiptDiscountBuilder extends IReceiptDiscountBuilder<IConstantReceiptDiscountBuilder> {

    IConstantReceiptDiscountBuilder setConstant(BigDecimal constant);
}
