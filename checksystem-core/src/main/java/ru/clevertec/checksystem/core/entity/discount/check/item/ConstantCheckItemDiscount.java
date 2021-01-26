package ru.clevertec.checksystem.core.entity.discount.check.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.clevertec.checksystem.core.common.IConstable;

import java.math.BigDecimal;

public abstract class ConstantCheckItemDiscount extends CheckItemDiscount implements IConstable<BigDecimal> {

    private BigDecimal constant;

    protected ConstantCheckItemDiscount() {
    }

    public ConstantCheckItemDiscount(String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(description);
        setConstant(constant);
    }

    public ConstantCheckItemDiscount(int id, String description, BigDecimal constant)
            throws IllegalArgumentException {
        super(id, description);
        setConstant(constant);
    }

    @JsonCreator
    public ConstantCheckItemDiscount(
            int id, String description, BigDecimal constant, CheckItemDiscount childDiscount)
            throws IllegalArgumentException {
        super(id, description, childDiscount);
        setConstant(constant);
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public void setConstant(BigDecimal constant)
            throws IllegalArgumentException {
        if (constant.doubleValue() < 0) {
            throw new IllegalArgumentException("Discount value cannot be less than 0");
        }
        this.constant = constant;
    }

    @Override
    public BigDecimal discountSum() {
        var childDiscountsSum = getChildDiscount() != null
                ? getChildDiscount().discountSum() : BigDecimal.ZERO;
        return childDiscountsSum.add(constant);
    }
}
