package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.clevertec.checksystem.core.common.IConstable;
import ru.clevertec.checksystem.core.exception.ArgumentNullException;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.math.BigDecimal;

public abstract class ConstantCheckDiscount extends CheckDiscount implements IConstable<BigDecimal> {

    private BigDecimal constant;

    protected ConstantCheckDiscount() {
    }

    public ConstantCheckDiscount(String description, BigDecimal constant) throws ArgumentNullException, ArgumentOutOfRangeException {
        super(description);
        setConstant(constant);
    }

    public ConstantCheckDiscount(int id, String description, BigDecimal constant) throws ArgumentOutOfRangeException {
        super(id, description);
        setConstant(constant);
    }

    @JsonCreator
    public ConstantCheckDiscount(
            int id, String description, BigDecimal constant, CheckDiscount dependentDiscount) throws ArgumentNullException, ArgumentOutOfRangeException {
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

        var itemsDiscountSum = getCheck().itemsDiscountAmount();

        return constant.add(dependentDiscountAmount).add(itemsDiscountSum);
    }
}
