package ru.clevertec.checksystem.core.entity.discount.check.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.clevertec.checksystem.core.common.IConstable;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.BigDecimal;

public abstract class ConstantCheckItemDiscount extends CheckItemDiscount implements IConstable<BigDecimal> {

    private BigDecimal constant;

    protected ConstantCheckItemDiscount() {
    }

    public ConstantCheckItemDiscount(String description, BigDecimal constant)
            throws ArgumentOutOfRangeException {
        super(description);
        setConstant(constant);
    }

    public ConstantCheckItemDiscount(int id, String description, BigDecimal constant)
            throws ArgumentOutOfRangeException {
        super(id, description);
        setConstant(constant);
    }

    @JsonCreator
    public ConstantCheckItemDiscount(
            int id, String description, BigDecimal constant, CheckItemDiscount dependentDiscount)
            throws ArgumentOutOfRangeException {
        super(id, description, dependentDiscount);
        setConstant(constant);
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) throws ArgumentOutOfRangeException {
        ThrowUtils.Argument.lessThan("constant", constant, BigDecimal.ZERO);
        this.constant = constant;
    }

    @Override
    public BigDecimal discountAmount() {
        var dependentDiscountAmount = getDependentDiscount() != null
                ? getDependentDiscount().discountAmount()
                : BigDecimal.ZERO;
        return dependentDiscountAmount.add(constant);
    }
}
