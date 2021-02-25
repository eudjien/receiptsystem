package ru.clevertec.checksystem.core.common.builder.discount.check;

import ru.clevertec.checksystem.core.common.builder.discount.IDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.entity.discount.check.CheckDiscount;

import java.util.Collection;

public interface ICheckDiscountBuilder<T extends ICheckDiscountBuilder<T>> extends IDiscountBuilder<CheckDiscount, T> {

    T setChecks(Collection<Check> checks);
}
