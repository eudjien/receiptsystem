package ru.clevertec.checksystem.core.discount.check.base;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageOffCheckDiscount extends CheckDiscount {

    private double discountPercent;

    public PercentageOffCheckDiscount(String description, double discountPercent) throws Exception {
        super(description);
        setDiscountPercent(discountPercent);
    }

    public PercentageOffCheckDiscount(int id, String description, double discountPercent) throws Exception {
        super(id, description);
        setDiscountPercent(discountPercent);
    }

    @JsonCreator
    public PercentageOffCheckDiscount(
            int id, String description, double discountPercent, CheckDiscount childDiscount) throws Exception {
        super(id, description, childDiscount);
        setDiscountPercent(discountPercent);
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double percent) throws Exception {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new Exception("Discount percent out of range (0-100)");
        }
        this.discountPercent = percent;
    }

    @Override
    public BigDecimal discountSum() {

        var subTotal = getCheck().subTotal();
        var childDiscountSum = BigDecimal.ZERO;
        var itemsDiscountSum = getCheck().itemsDiscountsSum();

        if (getChildDiscount() != null) {
            subTotal = subTotal.subtract(getChildDiscount().discountSum());
            childDiscountSum = getChildDiscount().discountSum();
        }

        subTotal = subTotal.subtract(itemsDiscountSum);

        var discount = subTotal.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(discountPercent));

        return discount.add(childDiscountSum).add(itemsDiscountSum);
    }
}
