package ru.clevertec.checksystem.core.common.builder.discount.check;

import java.math.BigDecimal;

public interface IConstantCheckDiscountBuilder extends ICheckDiscountBuilder<IConstantCheckDiscountBuilder> {

    IConstantCheckDiscountBuilder setConstant(BigDecimal constant);
}
