package ru.clevertec.checksystem.core.discount.item.base;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public abstract class ConstantCheckItemDiscount extends CheckItemDiscount {

    private BigDecimal discountValue;

    public ConstantCheckItemDiscount(String description, BigDecimal discountValue) throws Exception {
        super(description);
        setDiscountValue(discountValue);
    }

    public ConstantCheckItemDiscount(int id, String description, BigDecimal discountValue) throws Exception {
        super(id, description);
        setDiscountValue(discountValue);
    }

    @JsonCreator
    public ConstantCheckItemDiscount(
            int id, String description, BigDecimal discountValue, CheckItemDiscount childDiscount) throws Exception {
        super(id, description, childDiscount);
        setDiscountValue(discountValue);
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) throws Exception {
        if (discountValue.doubleValue() < 0) {
            throw new Exception("Discount value cannot be less than 0");
        }
        this.discountValue = discountValue;
    }

    @Override
    public BigDecimal discountSum() {
        var childDiscountsSum = getChildDiscount() != null
                ? getChildDiscount().discountSum() : BigDecimal.ZERO;
        return childDiscountsSum.add(discountValue);
    }
}
