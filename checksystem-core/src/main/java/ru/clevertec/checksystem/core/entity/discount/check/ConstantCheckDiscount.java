package ru.clevertec.checksystem.core.entity.discount.check;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.clevertec.checksystem.core.common.IConstable;

import java.math.BigDecimal;

public abstract class ConstantCheckDiscount extends CheckDiscount implements IConstable<BigDecimal> {

    private BigDecimal constant;

    protected ConstantCheckDiscount() {
    }

    public ConstantCheckDiscount(String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(description);
        setConstant(constant);
    }

    public ConstantCheckDiscount(int id, String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(id, description);
        setConstant(constant);
    }

    @JsonCreator
    public ConstantCheckDiscount(
            int id, String description, BigDecimal constant, CheckDiscount childDiscount)
            throws IllegalArgumentException {
        super(id, description, childDiscount);
        setConstant(constant);
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant) throws IllegalArgumentException {
        if (constant.doubleValue() < 0) {
            throw new IllegalArgumentException("Discount value cannot be less than 0");
        }
        this.constant = constant;
    }

    @Override
    public BigDecimal discountSum() {

        var childDiscountSum = getChildDiscount() != null
                ? getChildDiscount().discountSum() : BigDecimal.ZERO;
        var itemsDiscountSum = getCheck().itemsDiscountSum();

        return constant.add(childDiscountSum).add(itemsDiscountSum);
    }
}
