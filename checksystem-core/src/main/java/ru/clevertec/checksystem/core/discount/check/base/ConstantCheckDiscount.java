package ru.clevertec.checksystem.core.discount.check.base;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public abstract class ConstantCheckDiscount extends CheckDiscount {

    private BigDecimal discountValue;

    public ConstantCheckDiscount(String description, BigDecimal discountValue) throws Exception {
        super(description);
        setDiscountValue(discountValue);
    }

    public ConstantCheckDiscount(int id, String description, BigDecimal discountValue) throws Exception {
        super(id, description);
        setDiscountValue(discountValue);
    }

    @JsonCreator
    public ConstantCheckDiscount(
            int id, String description, BigDecimal discountValue, CheckDiscount childDiscount) throws Exception {
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

        var childDiscountSum = getChildDiscount() != null
                ? getChildDiscount().discountSum() : BigDecimal.ZERO;
        var itemsDiscountSum = getCheck().itemsDiscountsSum();

        return discountValue.add(childDiscountSum).add(itemsDiscountSum);
    }
}
