package ru.clevertec.checksystem.core.common.builder.discount.check.item;

import java.math.BigDecimal;

public interface IConstantCheckItemDiscountBuilder
        extends ICheckItemDiscountBuilder<IConstantCheckItemDiscountBuilder> {

    IConstantCheckItemDiscountBuilder setConstant(BigDecimal constant);
}
